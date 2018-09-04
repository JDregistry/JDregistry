package dockerregistry.internal.host

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
