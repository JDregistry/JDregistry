package jdregistry.client.auth

/**
 * Any exception that is related to failure with Authentication with the Docker Registry
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
data class DockerRegistryAuthenticationException(override val message: String) : Exception(message)
