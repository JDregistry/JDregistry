package jdregistry.client

import jdregistry.client.http.TestHttpClient
import jdregistry.client.payload.DockerRepositoryName
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
        Assert.assertTrue(DockerRepositoryName.of("testrepo0") in repos)
        Assert.assertTrue(DockerRepositoryName.of("testrepo1") in repos)
        Assert.assertTrue(DockerRepositoryName.of("testrepo2") in repos)
        Assert.assertTrue(repos.size > 2)
    }

    @Test fun list_tags_1() {

        val tags0 = client.listTags("testrepo0")
        val tags1 = client.listTags("testrepo1")
        val tags2 = client.listTags("testrepo2")

        Assert.assertTrue(tags0.name == "testrepo0")
        Assert.assertTrue(tags1.name == "testrepo1")
        Assert.assertTrue(tags2.name == "testrepo2")

        Assert.assertTrue(tags0.tags == null)
        val tags1Tags = tags1.tags
        Assert.assertTrue(tags1Tags != null && "latest" in tags1Tags && tags1Tags.size == 1)
        val tags2Tags = tags2.tags
        Assert.assertTrue(tags2Tags != null && "latest" in tags2Tags && "other" in tags2Tags && tags2Tags.size == 2)
    }
}