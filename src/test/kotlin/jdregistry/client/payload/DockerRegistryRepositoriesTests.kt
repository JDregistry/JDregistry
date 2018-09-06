package jdregistry.client.payload

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.benas.randombeans.api.EnhancedRandom
import org.junit.Assert
import org.junit.Test

class DockerRegistryRepositoriesTests {

    private companion object {
        const val ELEMENTS = 100
        val clazz = DockerRegistryRepositories::class.java

        fun assertEqualAfterSerialization(item: DockerRegistryRepositories) {

            // Object Mapper is recreated for reading and writing such that is ensured that it does not
            // keep state
            val serialized = jacksonObjectMapper().writeValueAsString(item)
            val read: DockerRegistryRepositories = jacksonObjectMapper().readValue(serialized)
            Assert.assertEquals(read, item)
        }

        /**
         * Asserts that serializing and deserializing an payload object does not alter
         * equivalence of the serialized object
         */
        fun assertAllEqualAfterSerialization(items: Iterable<DockerRegistryRepositories>) {

            items.forEach(Companion::assertEqualAfterSerialization)
        }
    }

    /*
     * Test serialization
     *
     */

    @Test fun equal_after_serialization_collection() {

        assertAllEqualAfterSerialization(EnhancedRandom.randomCollectionOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_list() {

        assertAllEqualAfterSerialization(EnhancedRandom.randomListOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_set() {

        assertAllEqualAfterSerialization(EnhancedRandom.randomSetOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_no_repositories_empty() {

        assertEqualAfterSerialization(DockerRegistryRepositories(emptyList()))
    }

    @Test fun equal_after_serialization_no_repositories_static() {

        assertEqualAfterSerialization(DockerRegistryRepositories(listOf()))
    }

    @Test fun equal_after_serialization_no_repositories_mutable() {

        assertEqualAfterSerialization(DockerRegistryRepositories(mutableListOf()))
    }

    @Test fun equal_after_serialization_no_repositories_array() {

        assertEqualAfterSerialization(DockerRegistryRepositories(arrayListOf()))
    }

    /*
     *   Test List Interface
     */
    @Test fun empty_list_creates_empty_repo() {

        Assert.assertTrue(DockerRegistryRepositories(emptyList()).isEmpty())
        Assert.assertTrue(DockerRegistryRepositories(listOf()).isEmpty())
        Assert.assertTrue(DockerRegistryRepositories(mutableListOf()).isEmpty())
        Assert.assertTrue(DockerRegistryRepositories(arrayListOf()).isEmpty())
        Assert.assertTrue(DockerRegistryRepositories(arrayListOf()).isEmpty())
    }

    @Test fun elements_in_list_are_equal_to_elements_in_repo() {

        val ls = EnhancedRandom.randomListOf(ELEMENTS, String::class.java)
        val repo = DockerRegistryRepositories(ls)
        ls.forEachIndexed { index, s ->
            Assert.assertEquals(s, repo[index])
        }
    }
}
