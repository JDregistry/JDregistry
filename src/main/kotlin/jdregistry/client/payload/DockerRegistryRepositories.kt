package jdregistry.client.payload

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jdregistry.client.payload.serialize.DockerRegistryRepositoriesDeserializer
import jdregistry.client.payload.serialize.DockerRegistryRepositoriesSerializer


/**
 * Represents the response from the Docker Registry when requesting contained repositories.
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
@JsonSerialize(using = DockerRegistryRepositoriesSerializer::class)
@JsonDeserialize(using = DockerRegistryRepositoriesDeserializer::class)
data class DockerRegistryRepositories(

       @JsonProperty("repositories") val repositories : List<String> // Never observed to be null for the tested registries

) : List<String> by repositories
