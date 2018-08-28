package dockerregistry.data

import dockerregistry.ICanonicalStringRepresentable


data class DockerRepositoryName(
        val firstComponent: PathComponent,
        val moreComponents: List<PathComponent> = emptyList()) : ICanonicalStringRepresentable {

    constructor(repr: String) : this(
            PathComponent(repr.split(SLASH).first()),
            repr.split(SLASH).drop(1).map(::PathComponent)
    )

    init {

        // Requirements: Taken the lengths of all components together,
        // the result must be lower than 256 characters
        val len = firstComponent.repr.length + moreComponents.sumBy { it.repr.length } + moreComponents.size
        require(len < LEN_BOUND) {

            "The number of characters in a DockerRepositoryName must be smaller than $LEN_BOUND"
        }
    }

    override val repr: String
        get() {

            return (listOf(firstComponent) + moreComponents).joinToString(separator = SLASH) { it.repr }
        }


    data class PathComponent(override val repr: String) : ICanonicalStringRepresentable {


        init {
            require(repr.matches(regex)) {

                "String '$repr' is not a valid Path Component for a Docker Registry"
            }
        }

        companion object {

            private val regex = Regex("[a-z0-9]+(?:[._-][a-z0-9]+)*")
        }
    }

    companion object {

        private const val SLASH = "/"
        private const val LEN_BOUND = 256
    }
}
