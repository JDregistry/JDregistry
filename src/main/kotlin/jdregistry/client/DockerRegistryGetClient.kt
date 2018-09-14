package jdregistry.client

import jdregistry.client.http.IHttpGetClient
import jdregistry.client.internal.client.DefaultGetClient
import jdregistry.client.internal.host.Host
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags
import jdregistry.client.data.DockerRepositoryName

/**
 * Interface for representing client that can communicate with a Docker Registry via HTTP GET.
 *
 * This client only supports performing requests with HTTP GET verb. This means that usage of this client
 * guarantees that the state of the targeted Docker Registry cannot be changed.
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
interface DockerRegistryGetClient {

    /**
     * The `hostname` which is used to access this registry
     *
     */
    val hostname: String

    /**
     * The port that the registry is going to be accessed at
     */
    val port: Int

    /**
     *  Lists all the available repositories in the Docker Registry.
     *
     *  @return [DockerRegistryRepositories] object that contains all repositories that this client could
     *  list.
     */
    fun listRepositories(): DockerRegistryRepositories

    /**
     * Lists all the tags for a specific repository, given as [String].
     *
     * @param repository The repository as [String] for which all tags should be listed.
     *
     * @return All tags of the requested repository, represented as [DockerRegistryTags] object.
     */
    fun listTags(repository: DockerRepositoryName): DockerRegistryTags

    /**
     * Checks whether the Registry targeted by this client supports the V2 API of Docker Registry
     *
     * @return Whether the targeted Docker Registry supports V2.
     */
    fun implementsV2RegistryAPI(): Boolean

    companion object {

        /**
         * Construct a [DockerRegistryGetClient] based on the provided remote parameters and the
         * authentication method
         *
         * @param host The host used to contact the Docker Registry
         * @param port The port at which the registry should be contacted
         * @param client The [IHttpGetClient] implementation used for performing GET requests
         * @param auth Authentication method used
         *
         * @return Instance of [DockerRegistryGetClient]
         *
         */
        fun of(host: String, port: Int, client: IHttpGetClient, auth: Authenticate? = null): DockerRegistryGetClient =
                DefaultGetClient(Host.of(host), port, client, auth)
    }
}
