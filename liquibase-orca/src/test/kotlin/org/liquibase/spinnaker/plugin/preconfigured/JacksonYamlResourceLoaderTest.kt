package org.liquibase.spinnaker.plugin.preconfigured

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.netflix.spinnaker.orca.clouddriver.config.KubernetesPreconfiguredJobProperties
import dev.minutest.junit.JUnit5Minutests
import dev.minutest.rootContext
import java.io.File
import kotlin.test.assertNotNull
import kotlin.test.assertTrue


class JacksonYamlResourceLoaderTest : JUnit5Minutests {
    fun tests() = rootContext {
        test("should successfully parse the liquibase job yaml configuration") {
            val subject: ObjectMapper = ObjectMapper(YAMLFactory()).disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            val job = subject.readValue(File("src/main/resources/org/liquibase/spinnaker/plugin/preconfigured/liquibase.yaml"), KubernetesPreconfiguredJobProperties::class.java)
            assertNotNull(job.account)
            assertNotNull(job.manifest)
            assertNotNull(job.manifest?.spec)
            assertTrue { !job.manifest?.spec?.template?.spec?.containers.isNullOrEmpty() }
        }
    }
}
