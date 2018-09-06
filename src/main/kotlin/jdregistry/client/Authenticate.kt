package jdregistry.client

import jdregistry.client.internal.auth.BasicAuth
import java.nio.charset.Charset

/**
 * Interface for specifying authentication methods
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
interface Authenticate {

    fun authorization(charset: Charset = Charsets.UTF_8) : String

    fun with(username: String, password: String): Authenticate = BasicAuth(username, password)
}
