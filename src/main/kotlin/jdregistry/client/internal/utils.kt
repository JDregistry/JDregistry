package jdregistry.client.internal

import java.net.URI

/**
 * Constructs new [URI] by either replacing or appending more query parameters
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
internal fun URI.withQuery(params: Map<String, String>, keepOld: Boolean): URI {

    // Construct  query string from map
    val queryExtend = params.map { "${it.key}=${it.value}" }.joinToString("&")

    // Platform type: String can be null
    val oldQuery: String? = this.query
    val newQuery = if (keepOld && oldQuery != null) {
        "$oldQuery&$queryExtend"
    } else {
        queryExtend
    }

    return URI(this.scheme, this.authority, this.path, newQuery, this.fragment)
}
