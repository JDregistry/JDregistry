package jdregistry.client.api.auth

import java.nio.charset.Charset
import java.util.Base64

/**
 * Interface for specifying authentication methods
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
interface Authenticate {

    /**
     * Returns the HTTP Header for the key 'Authorization'
     *
     * @param charset The charset to be used for encoding the Authorization
     * @return The [String] that represents the encoded value for the Authorization header.
     */
    fun authorization(charset: Charset = Charsets.UTF_8): String

    companion object {

        /**
         * Constructs a suitable [Authenticate] object based on the provided username and password
         *
         * @param username The username to use for authentication
         * @param password The pas
         *
         */
        fun with(username: String, password: String): Authenticate = BasicAuth(username, password)
    }

    private data class BasicAuth(
        val username: String,
        val password: String
    ) : Authenticate {

        private fun encodeBase64(charset: Charset = Charsets.UTF_8) =
                Base64.getEncoder().encodeToString("${username.trim()}:${password.trim()}".toByteArray(charset))

        override fun authorization(charset: Charset) = "Basic ${this.encodeBase64(charset)}"
    }
}
