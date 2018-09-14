package jdregistry.client.data

/**
 * Represents a Docker Tag for the Docker Registry
 *
 * @author Lukas Zimmermann
 * @since 0.0.4
 *
 */
interface DockerTag {

    val repr: String

    private data class GenericDockerTag(override val repr: String) : DockerTag {

        init {
            require(isValidTag(repr))
        }
        private companion object {
            val tagRegex = Regex("[a-zA-Z0-9_.-]+")
            fun isValidTag(input: String) =
                    ! input.startsWith('.') && ! input.startsWith('-') &&
                            input.length < 129 &&
                            input.matches(tagRegex)
        }
    }

    /**
     * Represents commonly used Docker Tags. Essentially, this only 'latest'
     *
     * @author Lukas Zimmermann
     * @see DockerTag
     * @since 0.0.4
     *
     */
    enum class Common(override val repr: String) : DockerTag {

        LATEST("latest")
    }

    companion object {

        // Short-wire the LATEST Tag
        val LATEST = Common.LATEST
        fun of(input: String) = if (LATEST.repr == input) LATEST else GenericDockerTag(input)
    }
}
