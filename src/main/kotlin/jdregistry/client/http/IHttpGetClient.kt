package jdregistry.client.http

import java.net.URI

/**
 * Interface for a HTTP client that only support the HTTP GET method on the provided URI.
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
interface IHttpGetClient {

    /**
     * Performs HTTP GET on the provided [URI].
     *
     * @param uri The [URI] to which HTTP GET should be sent to.
     * @return [IHttpResponse] object reflecting the result of the request
     */
    fun get(uri: URI, authorization: String? = null): IHttpResponse
}
