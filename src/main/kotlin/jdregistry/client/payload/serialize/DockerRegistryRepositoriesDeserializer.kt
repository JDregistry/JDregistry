package jdregistry.client.payload.serialize

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import jdregistry.client.payload.DockerRegistryRepositories
import jdregistry.client.data.DockerRepositoryName
import jdregistry.client.internal.Constants
import java.io.IOException

/**
 * The Deserializer for [DockerRegistryRepositories]
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class DockerRegistryRepositoriesDeserializer
@JvmOverloads constructor(clazz: Class<DockerRegistryRepositories>? = null) : StdDeserializer<DockerRegistryRepositories>(clazz) {

    @Throws(IOException::class, JsonProcessingException::class)
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): DockerRegistryRepositories {

        val node: JsonNode = jp.codec.readTree(jp)

        val repositories = node.get(Constants.REPOSITORIES)
        return DockerRegistryRepositories(
                if (repositories.isNull) null else repositories.map { DockerRepositoryName(it.asText()) }
        )
    }
}
