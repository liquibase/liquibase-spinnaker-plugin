package org.liquibase.spinnaker.plugin.preconfigured

import com.netflix.spinnaker.kork.plugins.api.PluginConfiguration

@PluginConfiguration
data class PluginConfig(var account: String?, var namespace: String?, var application: String?)
