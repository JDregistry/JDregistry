package jdregistry.client.payload

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jdregistry.client.data.RepositoryName as DockerRepositoryName
import org.junit.Assert
import org.junit.Test

class DockerRegistryTagsTests {

    private companion object {

        val DUMMY_REPO = DockerRepositoryName.from("dummy")

        fun assertEqualAfterSerialization(item: DockerRegistryTags) {

            // Object Mapper is recreated for reading and writing such that is ensured that it does not
            // keep state
            val serialized = jacksonObjectMapper().writeValueAsString(item)
            val read: DockerRegistryTags = jacksonObjectMapper().readValue(serialized)
            Assert.assertEquals(read, item)
        }

        /**
         * Asserts that serializing and deserializing an payload object does not alter
         * equivalence of the serialized object
         */
        fun assertAllEqualAfterSerialization(items: Iterable<DockerRegistryTags>) {

            items.forEach { assertEqualAfterSerialization(it) }
        }
    }

    /*
     * Test serialization
     *
     */

    @Test fun equal_after_serialization_collection() {

        assertAllEqualAfterSerialization(randomTags())
    }

    @Test fun equal_after_serialization_set() {

        assertAllEqualAfterSerialization(randomTags().toSet())
    }

    @Test fun equal_after_serialization_no_tags_empty() {

        assertEqualAfterSerialization(DockerRegistryTags(DUMMY_REPO, emptyList()))
    }

    @Test fun equal_after_serialization_no_repositories_static() {

        assertEqualAfterSerialization(DockerRegistryTags(DUMMY_REPO, listOf()))
    }

    @Test fun equal_after_serialization_no_repositories_mutable() {

        assertEqualAfterSerialization(DockerRegistryTags(DUMMY_REPO, mutableListOf()))
    }

    @Test fun equal_after_serialization_no_repositories_array() {

        assertEqualAfterSerialization(DockerRegistryTags(DUMMY_REPO, arrayListOf()))
    }
}
