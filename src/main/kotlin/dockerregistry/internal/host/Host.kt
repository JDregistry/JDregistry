package dockerregistry.internal.host

internal interface Host {

    val repr: String


    companion object {

        fun of(repr: String): Host = when {

            IP4Address.isValid(repr) -> IP4Address(repr)
            Hostname.isValid(repr) -> Hostname(repr)
            else -> throw IllegalArgumentException("Provided value is neither a valid IP4 Address nor a valid Hostname")
        }


    }
}
