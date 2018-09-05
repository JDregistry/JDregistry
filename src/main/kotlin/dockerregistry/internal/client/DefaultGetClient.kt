package dockerregistry.internal.client

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dockerregistry.Authenticate
import dockerregistry.DockerRegistryAuthenticationException
import dockerregistry.DockerRegistryClientException
import dockerregistry.DockerRegistryGetClient
import dockerregistry.http.IHttpGetClient
import dockerregistry.http.IHttpResponse
import dockerregistry.internal.data.BearerToken
import dockerregistry.internal.host.Host
import dockerregistry.internal.http.OK
import dockerregistry.internal.http.UNAUTHORIZED
import dockerregistry.internal.withQuery
import dockerregistry.payload.DockerRegistryRepositories
import dockerregistry.payload.DockerRegistryTags
import java.net.URI

/**
 * Implementation of the Docker Registry Client Interface for V2 Docker Registry.
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
internal class DefaultGetClient(
        host: Host,
        port: Int,
        private val client: IHttpGetClient,
        private val auth: Authenticate? = null) : DockerRegistryGetClient {

    private val mapper = jacksonObjectMapper()

    // Generates the V2 endpoint from Host and Port parameters
    private val endpointV2 = URI.create("http://${host.repr}:$port/v2/")

    // Catalog URI
    private val catalog = endpointV2.resolve("/v2/_catalog")

    /**
     * Peforms a GET request to the registry URI and tries to read the returned value when the request
     * performs successfully
     *
     */
    private inline fun <reified T : Any> readGetResponse(uri: URI): T {

        val response = client.get(uri)
        val statusCode = response.statusCode

        return when (statusCode) {

            OK -> return mapper.readValue(response.body)

            // Here we implement the protocol of the Docker Registry to solve the authentication challenge
            UNAUTHORIZED-> performAuthenticationChallenge(response, uri)

            else -> throw DockerRegistryClientException(
                    "Docker Registry has replied with status code $statusCode", statusCode, uri)
        }
    }

    private fun bearer(token: String) = "Bearer $token"


    private inline fun <reified T : Any> performAuthenticationChallenge(
            response: IHttpResponse, originalURI: URI): T {

        if (auth == null) {

            throw DockerRegistryAuthenticationException(
                    "Docker Registry requires authentication, but username/password was not provided")
        }

        // Extract the Authenticate challenge from the response headers
        val challenge = response.authenticate
                ?: throw DockerRegistryAuthenticationException(
                        "Docker Registry returned 401, but no authentication challenge was provided!")

        // Fold the Value String of the response headers to the challenge attributes
        val challengeAttributes: MutableMap<String, String> = challenge
                .map(Companion::extractKeyValuePairs)
                .fold(mutableMapOf()) { acc, m -> acc.putAll(m); acc }

        // Get the realm and remove from the challenge attribute
        val realm = challengeAttributes[REALM_KEY]?.let { URI.create(it) }
                ?: throw DockerRegistryAuthenticationException(
                        "Authenticate Header is present, but no realm was specified")
        challengeAttributes.remove(REALM_KEY)

        // TODO The challenge Attributes can contain advice for the charset of the Base64 encoding

        val challengeResponse = this.client.get(
                realm.withQuery(challengeAttributes, keepOld = true),
                auth.authorization())

        // If the response status is not 200, we could not get a bearer token
        if (challengeResponse.statusCode != OK) {

            throw DockerRegistryAuthenticationException("Failed to retrieve bearer token")
        }
        val token = mapper.readValue<BearerToken>(challengeResponse.body).token

        // Retry the original URI with the auth token
        val newReponse = this.client.get(originalURI, bearer(token))

        return mapper.readValue(newReponse.body)
    }


    override fun listRepositories(): DockerRegistryRepositories =

            readGetResponse(this.catalog)

    override fun listTags(repository: String): DockerRegistryTags =
            readGetResponse(this.catalog.resolve("/v2/$repository/tags/list"))

    override fun implementsV2RegistryAPI() = client.get(endpointV2).statusCode == 200

    companion object {

        // Regex used to extract key value pairs from header strings
        private val extractRegex = Regex("([a-z]+)=\"([^\"]*)\"")

        private const val REALM_KEY = "realm"

        private fun extractKeyValuePairs(input: String) =

                extractRegex.findAll(input).map {
                    val groupValues = it.groupValues
                    groupValues[1] to groupValues[2]
                }.toMap()
    }
}
