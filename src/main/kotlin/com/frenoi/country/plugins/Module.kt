package com.frenoi.country.plugins

import com.frenoi.country.configuration.MongoConnector
import com.frenoi.country.model.MongoDbProperties
import com.frenoi.country.repository.CountryRepository
import com.frenoi.country.repository.impl.CountryRepositoryImpl
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin

fun Application.configureModule() {
    install(Koin) {
        modules(
            module {
                single { MongoDbProperties(environment.config) }
                single { MongoConnector(get()).dataBase }
                single<CountryRepository> { CountryRepositoryImpl(get()) }
            }
        )
    }
}

