package com.frenoi.country.model

import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.Serializable

@Serializable
data class HealthInfo(
    private val port: UInt?,
    private val name: String?,
    private val uniqueId: String?,
    private val environment: String?,
    private val status: String? = "UP",
) {
    constructor(applicationConfig: ApplicationConfig?) : this(
        port = applicationConfig?.propertyOrNull("ktor.deployment.port")
            ?.getString()
            ?.toUInt(),
        name = applicationConfig?.propertyOrNull("application.name")
            ?.getString(),
        uniqueId = applicationConfig?.propertyOrNull("application.uniqueId")
            ?.getString(),
        environment = applicationConfig?.propertyOrNull("ktor.development")
            ?.getString()
            ?.toBooleanStrictOrNull()
            ?.let { if (it) "development" else "production" },
    )
}
