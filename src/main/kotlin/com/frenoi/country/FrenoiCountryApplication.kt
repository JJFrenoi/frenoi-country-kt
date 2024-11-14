package com.frenoi.country

import com.frenoi.country.plugins.configureHTTP
import com.frenoi.country.plugins.configureModule
import com.frenoi.country.plugins.configureMonitoring
import com.frenoi.country.plugins.configureRouting
import com.frenoi.country.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.module() {
    environment.log.info("Starting application module")
    configureHTTP()
    configureSerialization()
    configureModule()
    configureMonitoring()
    configureRouting()
}
    
