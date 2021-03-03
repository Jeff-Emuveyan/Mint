package com.seamfix.mint.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import co.paystack.android.model.Card
import com.seamfix.mint.data.repo.CardRepository
import com.seamfix.mint.model.CardDetailsResponse

class MainViewModel @ViewModelInject constructor(var cardRepository: CardRepository): ViewModel() {

    fun validateCardNumber(number: String?): Boolean {
        val card = Card(number, 0,0, "")
        return card.validNumber()
    }


    suspend fun fetchCardDetails(cardNumber: String): CardDetailsResponse? {
        return cardRepository.fetchCardDetails(cardNumber)
    }


}