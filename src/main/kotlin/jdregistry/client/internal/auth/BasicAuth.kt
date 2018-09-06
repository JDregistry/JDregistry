package jdregistry.client.internal.auth

import jdregistry.client.Authenticate
import java.nio.charset.Charset
import java.util.Base64

/**
 * Information needed for performing Http Basic Authenticate
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
internal data class BasicAuth(
    val username: String,
    val password: String
) : Authenticate {

    private fun encodeBase64(charset: Charset = Charsets.UTF_8) =
            Base64.getEncoder().encodeToString("${username.trim()}:${password.trim()}".toByteArray(charset))

    override fun authorization(charset: Charset) = "Basic ${this.encodeBase64(charset)}"
}
