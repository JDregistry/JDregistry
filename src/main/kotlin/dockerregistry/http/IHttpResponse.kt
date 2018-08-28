package dockerregistry.http


/**
 * Represents the response of a Http Request. Currently,
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
interface IHttpResponse {

    /**
     * The HTTP statusCode code, represented as [Int].
     */
    val statusCode: Int

    /**
     * The body of the HTTP response, represented as [String].
     */
    val body: String
}
