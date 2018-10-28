package jdregistry.client.api

import jdregistry.client.api.auth.Authenticate
import jdregistry.client.internal.client.DefaultGetClient
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags
import jdregistry.client.data.RepositoryName as DockerRepositoryName
import jdregistry.client.http.IHttpGetClient
import jdregistry.client.api.auth.DockerRegistryAuthenticationException
import java.net.URI

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
     * The URI under which the Docker Registry is available
     */
    val uri: URI

    /**
     *  Lists all the available repositories in the Docker Registry.
     *  @return [DockerRegistryRepositories] object that contains all repositories that this client could
     *  list.
     *  @throws DockerRegistryAuthenticationException if authentication for listing the repostiories failed
     */
    fun listRepositories(): DockerRegistryRepositories

    /**
     * Lists all the tags for a specific repository, given as [String].
     *
     * @param repository The repository as [String] for which all tags should be listed.
     *
     * @return All tags of the requested repository, represented as [DockerRegistryTags] object.
     * @throws DockerRegistryAuthenticationException if authentication for listing the repostiories failed
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
        fun of(uri: URI, client: IHttpGetClient, auth: Authenticate? = null): DockerRegistryGetClient =
                DefaultGetClient(uri, client, auth)
    }
}
