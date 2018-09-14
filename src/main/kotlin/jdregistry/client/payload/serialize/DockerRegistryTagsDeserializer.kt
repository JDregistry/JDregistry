package jdregistry.client.payload.serialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.payload.DockerRegistryTags
import jdregistry.client.data.DockerRepositoryName
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

        val repo = DockerRepositoryName(node.get("name").asText())
        val tags = node.get("tags")

        // val tags = (node.get("tags") as ArrayNode).map { it.asText()}
        return DockerRegistryTags(repo, if (tags.isNull) null else tags.map { it.asText() })
    }
}
