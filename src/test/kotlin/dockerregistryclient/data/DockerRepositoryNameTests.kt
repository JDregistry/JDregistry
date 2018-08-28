package dockerregistryclient.data

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class DockerRepositoryNameTests {

    private fun assertIllegalArgument(f: () -> Unit) {

        Assertions.assertThrows(IllegalArgumentException::class.java, f)
    }

    @Test fun test_constructor_valid_path_components() {

        DockerRepositoryName.PathComponent("alpine")
        DockerRepositoryName.PathComponent("nginx")
        DockerRepositoryName.PathComponent("python")
        DockerRepositoryName.PathComponent("test-repo")
    }


    @Test fun test_constructor_invalid_path_components() {

        assertIllegalArgument { DockerRepositoryName.PathComponent("") }
        assertIllegalArgument { DockerRepositoryName.PathComponent("_") }
        assertIllegalArgument { DockerRepositoryName.PathComponent("foo/bar") }
    }


    @Test fun test_constructors_are_equivalent() {

        mapOf(
                "alpine" to Pair(
                        DockerRepositoryName.PathComponent("alpine"),
                        emptyList()),
                "namespace/alpine" to Pair(
                        DockerRepositoryName.PathComponent("namespace"),
                        listOf(DockerRepositoryName.PathComponent("alpine")))

        ).forEach {

            Assertions.assertEquals(
                    DockerRepositoryName(it.key),
                    DockerRepositoryName(it.value.first, it.value.second)
            )
        }
    }
}
