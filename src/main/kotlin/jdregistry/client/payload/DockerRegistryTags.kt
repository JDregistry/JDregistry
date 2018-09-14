package jdregistry.client.payload

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jdregistry.client.data.DockerRepositoryName
import jdregistry.client.payload.serialize.DockerRegistryTagsDeserializer
import jdregistry.client.payload.serialize.DockerRegistryTagsSerializer

/**
 * Represents the Docker Registry response when requesting the tags for a particular repository
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
@JsonSerialize(using = DockerRegistryTagsSerializer::class)
@JsonDeserialize(using = DockerRegistryTagsDeserializer::class)
data class DockerRegistryTags(

    @JsonProperty("name")
    val name: DockerRepositoryName,

    @JsonProperty("tags")
    val tags: List<String>? // Must be nullable
)
