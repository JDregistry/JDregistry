package dockerregistry

import org.testcontainers.containers.GenericContainer


/**
 *
 * Implements a [GenericContainer] that exposes exactly one port for testing.
 *
 * @author Lukas Zimmermann
 * @since 0.0.1
 *
 */
class SingleExposedPortContainer(imageName: String, private val originalPort : Int)
    : GenericContainer<SingleExposedPortContainer>(imageName) {

    init {
        this.withExposedPorts(originalPort)
    }

    val mappedPort : Int by lazy { this.getMappedPort(this.originalPort) }
}
