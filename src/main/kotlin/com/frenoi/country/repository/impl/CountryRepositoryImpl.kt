package com.frenoi.country.repository.impl

import com.frenoi.country.model.Country
import com.frenoi.country.repository.CountryRepository
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.bson.Document

class CountryRepositoryImpl(private val mongoDatabase: MongoDatabase?) : CountryRepository {
    companion object {
        private const val COUNTRY_COLLECTION = "country"
    }

    override suspend fun findByCode(code: String?): Country? {
        return mongoDatabase?.getCollection<Document>(COUNTRY_COLLECTION)
            ?.find()
            ?.projection(Document(code, 1).append("_id", 0))
            ?.mapNotNull { document -> document.get(code) as? Document? }
            ?.map { document -> Country(code, document) }
            ?.firstOrNull()
    }
}
