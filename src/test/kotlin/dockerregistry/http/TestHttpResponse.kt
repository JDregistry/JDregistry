package dockerregistry.http

import org.apache.http.client.methods.CloseableHttpResponse
import java.io.ByteArrayOutputStream


/**
 * Test class for implementing [IHttpResponse].
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class TestHttpResponse(response : CloseableHttpResponse) : IHttpResponse {

    override val body: String
    override val statusCode = response.statusLine.statusCode
    init {
        val outstream = ByteArrayOutputStream()
        response.entity.writeTo(outstream)
        response.close()
        outstream.flush()
        outstream.close()
        this.body = outstream.toString()
    }
}
