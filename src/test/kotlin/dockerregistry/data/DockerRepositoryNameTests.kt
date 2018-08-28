package dockerregistry.data

import org.junit.Assert
import org.junit.Test


class DockerRepositoryNameTests {


    @Test
    fun test_constructor_valid_path_components() {

        DockerRepositoryName.PathComponent("alpine")
        DockerRepositoryName.PathComponent("nginx")
        DockerRepositoryName.PathComponent("python")
        DockerRepositoryName.PathComponent("test-repo")
    }


    @Test(expected = IllegalArgumentException::class)
    fun test_constructor_invalid_path_components1() {

        DockerRepositoryName.PathComponent("")
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_constructor_invalid_path_components2() {

        DockerRepositoryName.PathComponent("_")
    }

    @Test(expected = IllegalArgumentException::class)
    fun test_constructor_invalid_path_components3() {

        DockerRepositoryName.PathComponent("foo/bar")
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

            Assert.assertEquals(
                    DockerRepositoryName(it.key),
                    DockerRepositoryName(it.value.first, it.value.second)
            )
        }
    }
}
