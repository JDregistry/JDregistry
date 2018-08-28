package dockerregistry.http

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import java.net.URI


/**
 * Test class for implementing [IHttpClient].
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class TestHttpClient : IHttpClient {

    private val client = HttpClientBuilder.create().build()

    override fun get(uri: URI): IHttpResponse = TestHttpResponse(client.execute(HttpGet(uri)))
}
