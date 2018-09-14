package jdregistry.client.payload

import jdregistry.client.data.DockerRepositoryName
import jdregistry.client.data.DockerTag
import java.util.Random
import kotlin.streams.asSequence

private const val REPLICAS = 300

internal fun randomInt(max: Int) = (1..max).shuffled().first()

internal fun randomString(max: Int): String {

    val source = "abcdefghijklmnopqrstuvwxyz0123456789"
    return Random().ints(randomInt(max).toLong(), 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
}

internal fun randomDockerRepositoryName(): DockerRepositoryName {

    val maxSLength = randomInt(29)
    var nElements = Math.floor(255.0 / maxSLength).toInt()
    while ((maxSLength * nElements) + nElements > 255) {
        nElements--
    }
    val ls = List(nElements) { randomString(maxSLength) }
    return DockerRepositoryName(ls.joinToString("/"))
}

internal fun randomRepositories(): List<DockerRegistryRepositories> =

        // work with replicas
        List(REPLICAS) { _ ->

            val nRepos = randomInt(100)
            DockerRegistryRepositories(List(nRepos) { randomDockerRepositoryName() })
        }

private fun randomTag() = DockerTag.of(randomString(128))

internal fun randomTags(): List<DockerRegistryTags> =

        List(REPLICAS) { _ ->

            DockerRegistryTags(
                    randomDockerRepositoryName(), List(100) { randomTag() })
        }
