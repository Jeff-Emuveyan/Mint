package com.seamfix.mint.data.source.remote

import com.seamfix.mint.model.CardDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {

    @GET("{card_number}")
    suspend fun getCardDetails(@Path("card_number")
                                   number: String): Response<CardDetailsResponse>
}