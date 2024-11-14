package com.frenoi.country.plugins

import com.frenoi.country.model.HealthInfo
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.metrics.micrometer.MicrometerMetrics
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry

fun Application.configureMonitoring() {
    val appMicrometerRegistry = PrometheusMeterRegistry(PrometheusConfig.DEFAULT)
    val healthInfo = HealthInfo(environment.config)
    environment.log.info(healthInfo.toString())
    install(MicrometerMetrics) {
        meterBinders = listOf(
            JvmMemoryMetrics(),
            JvmGcMetrics(),
            ProcessorMetrics()
        )
        registry = appMicrometerRegistry
    }
    routing {
        route("/actuator") {
            get("/metrics") {
                call.respondText(appMicrometerRegistry.scrape())
            }
            get("/health") {
                call.respond(healthInfo)
            }
        }
    }
}

