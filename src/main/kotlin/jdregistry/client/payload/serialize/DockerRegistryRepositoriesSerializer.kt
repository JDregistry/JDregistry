package jdregistry.client.payload.serialize

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import jdregistry.client.payload.DockerRegistryRepositories

/**
 * The Serializer for [DockerRegistryRepositories]
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class DockerRegistryRepositoriesSerializer @JvmOverloads constructor(clazz: Class<DockerRegistryRepositories>? = null)
    : StdSerializer<DockerRegistryRepositories>(clazz) {

    override fun serialize(value: DockerRegistryRepositories, gen: JsonGenerator, provider: SerializerProvider) {

        with(gen) {
            writeStartObject()
            writeFieldName("repositories")
            writeStartArray()
            value.forEach { writeString(it.asString()) }
            writeEndArray()
            writeEndObject()
        }
    }
}
