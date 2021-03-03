package com.seamfix.mint.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import co.paystack.android.model.Card
import com.seamfix.mint.data.repo.CardRepository
import com.seamfix.mint.model.CardDetailsResponse

class MainViewModel @ViewModelInject constructor(var cardRepository: CardRepository): ViewModel() {

    /*** This method checks if a card number is valid or not
     * @param number: the user's card number.
     * ***/
    fun validateCardNumber(number: String?): Boolean {
        val card = Card(number, 0,0, "")
        return card.validNumber()
    }

    /*** Make a network call to get the details of the user's card ***/
    suspend fun fetchCardDetails(cardNumber: String): CardDetailsResponse? {
        return cardRepository.fetchCardDetails(cardNumber)
    }


}