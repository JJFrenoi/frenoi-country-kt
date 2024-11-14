package com.frenoi.country.repository

import com.frenoi.country.model.Country

interface CountryRepository {
    suspend fun findByCode(code: String?): Country?
}
