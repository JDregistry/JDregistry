package jdregistry.client.payload.serialize

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import jdregistry.client.internal.Constants
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags

/**
 * The Serializer for [DockerRegistryRepositories]
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class DockerRegistryTagsSerializer @JvmOverloads constructor(clazz: Class<DockerRegistryTags>? = null)
    : StdSerializer<DockerRegistryTags>(clazz) {

    override fun serialize(value: DockerRegistryTags, gen: JsonGenerator, provider: SerializerProvider?) {
        with(gen) {
            writeStartObject()

            // Write 'name'
            writeFieldName(Constants.NAME)
            writeString(value.name.asString())
            // Write 'tags'
            writeFieldName(Constants.TAGS)
            writeStartArray()
            value.tags?.forEach { gen.writeString(it.repr) }
            writeEndArray()
            writeEndObject()
        }
    }
}
