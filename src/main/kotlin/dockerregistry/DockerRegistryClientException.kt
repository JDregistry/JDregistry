package dockerregistry

import java.net.URI

data class DockerRegistryClientException(
        override val message: String,
        val statusCode: Int,
        val remote: URI,
        val method: String = "GET") : Exception(message)
