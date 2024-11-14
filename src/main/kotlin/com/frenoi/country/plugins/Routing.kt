package com.frenoi.country.plugins

import com.frenoi.country.repository.CountryRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val countryRepository by inject<CountryRepository>()
    val codeRegex = Regex("^[A-Z]{2}\$")
    routing {
        route("/api/country") {
            get("/{code?}") {
                val requestCountryCode = call.parameters["code"]
                requestCountryCode?.takeIf { code -> codeRegex.matches(code) }
                    ?.let { code -> countryRepository.findByCode(code) }
                    ?.let { country -> call.respond(country) } ?: call.respond(
                    HttpStatusCode.NotFound,
                    "Country not found $requestCountryCode"
                )
            }
        }
    }
}
