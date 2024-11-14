package com.frenoi.country.model

import io.ktor.server.config.ApplicationConfig

data class MongoDbProperties(
    val uri: String?,
    val database: String?,
) {
    constructor(applicationConfig: ApplicationConfig?) : this(
        uri = applicationConfig?.propertyOrNull("ktor.mongo.uri")
            ?.getString(),
        database = applicationConfig?.propertyOrNull("ktor.mongo.database")
            ?.getString(),
    )
}
