package jdregistry.client.payload

import com.fasterxml.jackson.annotation.JsonProperty
import jdregistry.client.data.RepositoryName as DockerRepositoryName
import jdregistry.client.internal.Constants

/**
 * Represents the response from the Docker Registry when requesting contained repositories.
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
data class DockerRegistryRepositories(

    // Must be nullable, because the Docker Registry V2 was observed to return null here
    @JsonProperty(Constants.REPOSITORIES)
    val repositories: List<DockerRepositoryName>?
)
