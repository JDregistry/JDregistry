package dockerregistry

import dockerregistry.http.TestHttpClient
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test

class DockerRegistryGetClientNoAuthTests {


    companion object {

        // Container that is used for fetching Docker Registry Notifications
        @ClassRule
        @JvmField
        val REGISTRY = SingleExposedPortContainer("lukaszimmermann/test-registry:2.6.2", 5000)

      lateinit var client: DockerRegistryGetClient

        @BeforeClass @JvmStatic fun beforeClass() {

            client = DockerRegistryGetClient.of(REGISTRY.containerIpAddress, REGISTRY.mappedPort, TestHttpClient())
        }
    }

    @Test fun valid_v2_api() {

        Assert.assertTrue(client.implementsV2RegistryAPI())
    }


    @Test fun list_repos_1() {

        val repos = client.listRepositories()
        Assert.assertTrue("testrepo0" in repos)
        Assert.assertTrue("testrepo1" in repos)
        Assert.assertTrue("testrepo2" in repos)
        Assert.assertTrue(repos.size > 2)
    }
}