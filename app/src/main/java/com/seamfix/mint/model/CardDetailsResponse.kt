package com.seamfix.mint.model

data class CardDetailsResponse(
	val number: Number? = null,
	val country: Country? = null,
	val bank: Bank? = null,
	val scheme: String? = null,
	val prepaid: Boolean? = null,
	val type: String? = null,
	val brand: String? = null
)

data class Country(
	val emoji: String? = null,
	val latitude: Int? = null,
	val alpha2: String? = null,
	val name: String? = null,
	val numeric: String? = null,
	val currency: String? = null,
	val longitude: Int? = null
)

data class Bank(
	val phone: String? = null,
	val city: String? = null,
	val name: String? = null,
	val url: String? = null
)

data class Number(
	val length: Int? = null,
	val luhn: Boolean? = null
)

