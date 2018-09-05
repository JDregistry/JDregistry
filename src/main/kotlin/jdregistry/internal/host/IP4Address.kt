package jdregistry.internal.host

internal data class IP4Address(

        override val repr: String
) : Host {

    init {
        require(isValid(repr))
    }

    internal companion object {

        /**
         * Checks whether the provided [String] is a valid representation of an IP4 Address.
         *
         * @param repr The [String] representation that needs to be checked.
         *
         * @return Whether the provided [String] is a valid representation of an IP4 address
         */
        fun isValid(repr: String) = try {

            val spt = repr.split('.').map { it.toInt() }
            spt.size == 4 && spt.all { it in 0..255 }

        } catch (e: NumberFormatException) {

            false
        }
    }
}
