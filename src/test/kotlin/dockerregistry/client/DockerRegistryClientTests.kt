package dockerregistry.client

import dockerregistry.SingleExposedPortContainer
import dockerregistry.http.TestHttpClient
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test

class DockerRegistryClientTests {


    companion object {

        // Container that is used for fetching Docker Registry Notifications
        @ClassRule
        @JvmField
        val REGISTRY = SingleExposedPortContainer("lukaszimmermann/test-registry:2.6.2", 5000)

        lateinit var client: DockerRegistryClient


        @BeforeClass @JvmStatic fun beforeClass() {

            client = DockerRegistryClient(
                    REGISTRY.getExternalURI(),
                    TestHttpClient()
            )
        }
    }

    @Test fun valid_v2_api() {

        Assert.assertTrue(client.implementsV2RegistryAPI())
    }
}