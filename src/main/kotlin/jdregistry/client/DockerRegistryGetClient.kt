package jdregistry.client

import jdregistry.client.http.IHttpGetClient
import jdregistry.client.internal.client.DefaultGetClient
import jdregistry.client.internal.host.Host
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags
import java.net.URI


/**
 * Interface for representing client that can communicate with a Docker Registry via HTTP GET.
 *
 * This client only supports performing requests with HTTP GET verb. This means that usage of this client
 * guarantees that this client cannot change the state of the remote Docker Registry.
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
interface DockerRegistryGetClient {

    /**
     * The URI that the Docker Registry is going to be accessed
     */
    val uri: URI

    /**
     *  Lists all the available repositories in the Docker Registry.
     *
     *  @return [DockerRegistryRepositories] object that contains all repositories that the
     *  Docker Registry contains.
     *
     */
    fun listRepositories(): DockerRegistryRepositories

    /**
     * Lists all the tags for a specific repository.
     *
     * @param repository The repository as [String] for which all tags should be listed.
     *
     * @return All tags of the requested repository, represented as [DockerRegistryTags].
     */
    fun listTags(repository: String): DockerRegistryTags

    /**
     * Checks whether the Registry targeted by this client supports the V2 API of Docker Registry
     *
     */
    fun implementsV2RegistryAPI(): Boolean


   companion object {

       fun of(host: String, port: Int, client: IHttpGetClient, auth: Authenticate? = null) : DockerRegistryGetClient =
               DefaultGetClient(Host.of(host), port, client, auth)
   }
}
