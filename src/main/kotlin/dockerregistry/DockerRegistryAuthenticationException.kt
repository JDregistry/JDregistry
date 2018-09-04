package dockerregistry

data class DockerRegistryAuthenticationException(override val message: String) : Exception(message)
