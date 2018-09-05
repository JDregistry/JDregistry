package jdregistry

import jdregistry.internal.auth.BasicAuth
import java.nio.charset.Charset

interface Authenticate {

    fun authorization(charset: Charset = Charsets.UTF_8) : String

    fun with(username: String, password: String): Authenticate = BasicAuth(username, password)
}
