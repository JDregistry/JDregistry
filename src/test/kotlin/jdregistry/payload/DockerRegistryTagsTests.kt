package jdregistry.payload

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.github.benas.randombeans.api.EnhancedRandom
import org.junit.Assert
import org.junit.Test


class DockerRegistryTagsTests {

    private companion object {
        const val ELEMENTS = 100
        val clazz = DockerRegistryTags::class.java

        fun assertEqualAfterSerialization(item: DockerRegistryTags) {

            // Object Mapper is recreated for reading and writing such that is ensured that it does not
            // keep state
            val serialized = ObjectMapper().writeValueAsString(item)
            val read : DockerRegistryTags = ObjectMapper().readValue(serialized)
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

        assertAllEqualAfterSerialization(EnhancedRandom.randomCollectionOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_list() {

        assertAllEqualAfterSerialization(EnhancedRandom.randomListOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_set() {

        assertAllEqualAfterSerialization(EnhancedRandom.randomSetOf(ELEMENTS, clazz))
    }

    @Test fun equal_after_serialization_no_tags_empty() {

        assertEqualAfterSerialization(DockerRegistryTags("", emptyList()))
    }

    @Test fun equal_after_serialization_no_repositories_static() {

        assertEqualAfterSerialization(DockerRegistryTags("", listOf()))
    }

    @Test fun equal_after_serialization_no_repositories_mutable() {

        assertEqualAfterSerialization(DockerRegistryTags("", mutableListOf()))
    }

    @Test fun equal_after_serialization_no_repositories_array() {

        assertEqualAfterSerialization(DockerRegistryTags("",arrayListOf()))
    }
}
