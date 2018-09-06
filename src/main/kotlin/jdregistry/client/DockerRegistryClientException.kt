package jdregistry.client

import java.net.URI

/**
 *  Exception related to any unspecified error with the Docker Registry
 *
 *  @author Lukas Zimmermann
 *  @since 0.1
 */
data class DockerRegistryClientException(
        override val message: String,
        val statusCode: Int,
        val remote: URI,
        val method: String = "GET") : Exception(message)
