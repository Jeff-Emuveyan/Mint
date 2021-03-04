package com.seamfix.mint

import android.content.Context
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.seamfix.mint.data.repo.CardRepository
import com.seamfix.mint.data.source.remote.Service
import com.seamfix.mint.fakes.FakeResponseBody
import com.seamfix.mint.model.CardDetailsResponse
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response

class CardRepositoryTest {

    private val cardNumber = "101010101"
    private val subject: CardRepository = CardRepository(null)
    private val service: Service = mock()
    private val context: Context = mock()

    @Before
    fun setUp(){

    }

    @Test
    fun `card details should be returned when response code is 200`() = runBlocking{

        val successResponse = CardDetailsResponse(brand = "Gold", verificationStatus = true)
        Mockito.`when`(service.getCardDetails(cardNumber)).thenReturn(Response.success(successResponse))

        subject.service = service

        val response = subject.fetchCardDetails(cardNumber)
        MatcherAssert.assertThat(response?.verificationStatus,
            CoreMatchers.`is`(CoreMatchers.equalTo(true)))
    }

    @Test
    fun `An unverified card detail object should be returned when response code is 404`() = runBlocking{

        Mockito.`when`(service.getCardDetails(cardNumber))
            .thenReturn(Response.error(404, FakeResponseBody()))

        Mockito.`when`(context.getString(any()))
            .thenReturn("No match for this card was found.")

        subject.service = service
        subject.context = context

        val response = subject.fetchCardDetails(cardNumber)

        MatcherAssert.assertThat(response?.verificationStatus,
            CoreMatchers.`is`(CoreMatchers.equalTo(false)))

        MatcherAssert.assertThat(response?.errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo("No match for this card was found.")))

    }
}