package jdregistry.client.internal.host


/**
 * Represents a remote host via a Hostname
 *
 * @author Lukas Zimmermann
 * @since 0.1
 *
 */
internal data class Hostname(

        override val repr: String
) : Host {

    init {
        require(isValid(repr))
    }

    internal companion object {

        private val hostnameRegex =
                Regex("\\p{Alpha}(?:[\\p{Alnum}-]*\\p{Alnum})?(?:\\.\\p{Alpha}(?:[\\p{Alnum}-]*\\p{Alnum})?)*")

        fun isValid(repr: String) =
                repr.matches(hostnameRegex)
    }
}
