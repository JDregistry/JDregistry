package dockerregistry.data

import com.fasterxml.jackson.annotation.JsonProperty

data class DockerRegistryRepositories(

        @JsonProperty("repositories") val repositories: List<DockerRepositoryName>
)
