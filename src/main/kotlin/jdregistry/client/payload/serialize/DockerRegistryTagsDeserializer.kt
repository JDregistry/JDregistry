package jdregistry.client.payload.serialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags
import jdregistry.client.data.DockerRepositoryName
import jdregistry.client.data.DockerTag
import jdregistry.client.internal.Constants
import java.io.IOException

/**
 * The Deserializer for [DockerRegistryRepositories]
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class DockerRegistryTagsDeserializer
@JvmOverloads constructor(clazz: Class<DockerRegistryTags>? = null) : StdDeserializer<DockerRegistryTags>(clazz) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DockerRegistryTags {

        val node: JsonNode = jp.codec.readTree(jp)

        val repo = DockerRepositoryName(node.get(Constants.NAME).asText()) // Name is not nullable
        val tags = node.get(Constants.TAGS)
        return DockerRegistryTags(
                repo,
                if (tags.isNull) null else tags.map { DockerTag.of(it.asText()) })
    }
}
