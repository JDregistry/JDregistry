package dockerregistry.payload.serialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.ArrayNode
import dockerregistry.payload.DockerRegistryRepositories
import java.io.IOException


class DockerRegistryRepositoriesDeserializer
@JvmOverloads constructor(clazz: Class<DockerRegistryRepositories>? = null) : StdDeserializer<DockerRegistryRepositories>(clazz) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DockerRegistryRepositories {

        val node : JsonNode = jp.codec.readTree(jp)
        val repos = (node.get("repositories") as ArrayNode).map { it.asText() }
        return DockerRegistryRepositories(repos)
    }
}
