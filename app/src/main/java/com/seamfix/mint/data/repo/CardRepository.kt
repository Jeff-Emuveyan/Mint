package com.seamfix.mint.data.repo

import android.content.Context
import com.seamfix.mint.data.source.remote.ApiClient
import com.seamfix.mint.data.source.remote.Service
import com.seamfix.mint.model.CardDetailsResponse
import javax.inject.Inject


open class CardRepository @Inject constructor(var context: Context?) {

    var service: Service? = ApiClient.getClient(context)?.create(Service::class.java)

    open suspend fun fetchCardDetails(cardNumber: String): CardDetailsResponse? {
        return try {
            val response = service?.getCardDetails(cardNumber) ?: return null
            if(response.code() == 200 && response.body() != null){ //response form server:
                val  cardResponse = response.body() as CardDetailsResponse
                cardResponse
            }else{//We assume that this is a network error:
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}