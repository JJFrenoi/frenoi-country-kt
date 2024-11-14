package com.frenoi.country.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bson.Document

@Serializable
data class Country(
    @SerialName("code")
    val code: String? = null,
    @SerialName("emoji")
    val emoji: String? = null,
    @SerialName("image")
    val image: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("unicode")
    val unicode: String? = null
) {
    constructor(code: String?, document: Document) : this(
        code = code,
        emoji = document.getString("emoji"),
        name = document.getString("name"),
        unicode = document.getString("unicode"),
        image = document.getString("image")
    )
}
