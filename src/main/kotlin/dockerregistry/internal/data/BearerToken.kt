package dockerregistry.internal.data

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * The Bearer token issued by a Docker Registry Authenticate service.
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
internal data class BearerToken(

        @JsonProperty("token") val token: String,
        @JsonProperty("expires_in") val expires_in: Int,
        @JsonProperty("issued_at") val issued_at: String
)
