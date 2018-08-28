package dockerregistry.client

import dockerregistry.http.IHttpClient
import java.net.URI


/**
 * The Docker Registry Client of this library, supporting the V2 endpoints.
 *
 * @author Lukas Zimmermann
 * @since 0.1-SNAPSHOT
 *
 */
class DockerRegistryClient(
        uri: URI,
        private val client: IHttpClient) {

    private val v2Endpoint = uri.resolve("/v2/")


    fun implementsV2RegistryAPI() = client.get(v2Endpoint).statusCode == 200
}
