package jdregistry.http

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import java.net.URI


/**
 * Test class for implementing [IHttpGetClient].
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class TestHttpClient : IHttpGetClient {

    private val client = HttpClientBuilder.create().build()

    override fun get(uri: URI, authorization: String?): IHttpResponse = TestHttpResponse(client.execute(HttpGet(uri)))
}
