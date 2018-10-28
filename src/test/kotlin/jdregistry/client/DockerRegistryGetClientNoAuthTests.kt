package jdregistry.client

import jdregistry.client.api.DockerRegistryGetClient
import jdregistry.client.data.RepositoryName as DockerRepositoryName
import jdregistry.client.data.Tag as DockerTag
import jdregistry.client.impl.http.apache.ApacheHttpClient
import jdregistry.test.SingleExposedPortContainer
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Test
import java.net.URI

class DockerRegistryGetClientNoAuthTests {

    private companion object {

        // Container that is used for fetching Docker Registry Notifications
        @ClassRule
        @JvmField
        val REGISTRY = SingleExposedPortContainer("lukaszimmermann/test-registry:2.6.2", 5000)

        lateinit var client: DockerRegistryGetClient

        @BeforeClass @JvmStatic fun beforeClass() {

            client = DockerRegistryGetClient.of(
                    URI.create("http://${REGISTRY.containerIpAddress}:${REGISTRY.mappedPort}"),
                    ApacheHttpClient())
        }

        // The repositories
        val repo1 = DockerRepositoryName.from("testrepo0")
        val repo2 = DockerRepositoryName.from("testrepo1")
        val repo3 = DockerRepositoryName.from("testrepo2")
        val repo4 = DockerRepositoryName.from("namespace/testrepo0")
        val repo5 = DockerRepositoryName.from("namespace/testrepo1")
        val repo6 = DockerRepositoryName.from("namespace/testrepo2")

        val TAG_OTHER = DockerTag.from("other")
    }

    @Test fun valid_v2_api() {

        Assert.assertTrue(client.implementsV2RegistryAPI())
    }

    @Test fun list_repos_1() {

        val repos = client.listRepositories()
        val repositories = repos.repositories
        Assert.assertTrue(repositories != null && repo1 in repositories)
        Assert.assertTrue(repositories != null && repo2 in repositories)
        Assert.assertTrue(repositories != null && repo3 in repositories)

        Assert.assertTrue(repositories != null && repo4 in repositories)
        Assert.assertTrue(repositories != null && repo5 in repositories)
        Assert.assertTrue(repositories != null && repo6 in repositories)

        Assert.assertTrue(repositories != null && repositories.size > 5)
    }

    @Test fun list_tags_1() {

        val tags0 = client.listTags(repo1)
        val tags1 = client.listTags(repo2)
        val tags2 = client.listTags(repo3)

        Assert.assertTrue(tags0.name == repo1)
        Assert.assertTrue(tags1.name == repo2)
        Assert.assertTrue(tags2.name == repo3)

        Assert.assertTrue(tags0.tags == null)
        val tags1Tags = tags1.tags
        Assert.assertTrue(tags1Tags != null && DockerTag.LATEST in tags1Tags && tags1Tags.size == 1)
        val tags2Tags = tags2.tags
        Assert.assertTrue(tags2Tags != null && DockerTag.LATEST in tags2Tags && TAG_OTHER in tags2Tags && tags2Tags.size == 2)
    }

    @Test fun list_tags_2() {

        val tags0 = client.listTags(repo4)
        val tags1 = client.listTags(repo5)
        val tags2 = client.listTags(repo6)

        Assert.assertTrue(tags0.name == repo4)
        Assert.assertTrue(tags1.name == repo5)
        Assert.assertTrue(tags2.name == repo6)

        Assert.assertTrue(tags0.tags == null)
        val tags1Tags = tags1.tags
        Assert.assertTrue(tags1Tags != null && DockerTag.LATEST in tags1Tags && tags1Tags.size == 1)
        val tags2Tags = tags2.tags
        Assert.assertTrue(tags2Tags != null && DockerTag.LATEST in tags2Tags && TAG_OTHER in tags2Tags && tags2Tags.size == 2)
    }
}