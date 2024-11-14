package com.frenoi.country.configuration

import com.frenoi.country.model.MongoDbProperties
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase

class MongoConnector(private val mongoDbProperties: MongoDbProperties) {
    private val mongoClient: MongoClient? = mongoDbProperties.uri?.let {
        MongoClient.create(it)
    }
    val dataBase: MongoDatabase?
        get() = mongoDbProperties.database?.let { mongoClient?.getDatabase(it) }
}

