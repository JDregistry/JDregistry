package jdregistry.client.payload

import com.fasterxml.jackson.annotation.JsonProperty
import jdregistry.client.data.RepositoryName as DockerRepositoryName
import jdregistry.client.data.Tag as DockerTag
import jdregistry.client.internal.Constants

/**
 * Represents the Docker Registry response when requesting the tags for a particular repository
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
data class DockerRegistryTags(

    @JsonProperty(Constants.NAME)
    val name: DockerRepositoryName,

    // Must be nullable, because the Docker Registry V2 was observed to return null here
    @JsonProperty(Constants.TAGS)
    val tags: List<DockerTag>?
)
