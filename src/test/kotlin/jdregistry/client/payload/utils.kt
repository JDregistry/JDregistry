package jdregistry.client.payload

import java.util.Random
import kotlin.streams.asSequence

internal fun randomInt(max: Int) = (1..max).shuffled().first()

internal fun randomString(max: Int): String {

    val source = "abcdefghijklmnopqrstuvwxyz0123456789"
    return Random().ints(randomInt(max).toLong(), 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
}

internal fun DockerRepositoryName.Companion.random(): DockerRepositoryName {

    val maxSLength = randomInt(29)
    var nElements = Math.floor(255.0 / maxSLength).toInt()
    while ((maxSLength * nElements) + nElements > 255) {
        nElements--
    }
    val ls = List(nElements) { randomString(maxSLength) }
    return DockerRepositoryName.of(ls.joinToString("/"))
}

internal fun randomRepositories(): List<DockerRegistryRepositories> =

        // work with replicas
        List(300) { _ ->

            val nRepos = randomInt(100)
            DockerRegistryRepositories(List(nRepos) { DockerRepositoryName.random() })
        }
