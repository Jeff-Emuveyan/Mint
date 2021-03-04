package com.seamfix.mint

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.seamfix.mint.data.repo.CardRepository
import com.seamfix.mint.data.source.remote.Service
import com.seamfix.mint.util.enqueueResponse
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 *
 * This class tests the CardRepository class.
 */
@RunWith(AndroidJUnit4::class)
class CardRepositoryInstrumentedTest {

    private var mockWebServer = MockWebServer()
    private val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    private val subject = CardRepository(appContext)

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url(""))
        .client(client)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(Service::class.java)


    @Before
    fun setup() {
        subject.service = service
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    /*** This test ensures that CardRepository returns actual card details when the response code is
     * 200.
     * ***/
    @Test
    fun code200ShouldReturnCardDetails()= runBlocking {

        mockWebServer.enqueueResponse("success.json", 200)

        val response = subject.fetchCardDetails("20202020")

        //check that the verification status is set to true:
        MatcherAssert.assertThat(response?.verificationStatus,
            CoreMatchers.`is`(CoreMatchers.equalTo(true)))

        //check that the card's country matches what was expected:
        MatcherAssert.assertThat(response?.country?.name,
            CoreMatchers.`is`(CoreMatchers.equalTo("Denmark")))
    }

    /*** This test ensures that CardRepository returns a CardResponse object whose verificationStatus
     * has been set to false when the response code is 404.
     * 404 means that the card could not be verified.
     * ***/
    @Test
    fun code404ShouldReturnAnUnVerifiedCardResponse()= runBlocking {

        mockWebServer.enqueueResponse("404.json", 404)

        val response = subject.fetchCardDetails("00000000")

        MatcherAssert.assertThat(response?.verificationStatus,
            CoreMatchers.`is`(CoreMatchers.equalTo(false)))

        MatcherAssert.assertThat(response?.errorMessage,
            CoreMatchers.`is`(CoreMatchers.equalTo("No match for this card was found.")))
    }


    /*** This test ensures that CardRepository returns null when there is a network error ***/
    @Test
    fun shouldReturnNullWhenNetworkFails()= runBlocking {

        mockWebServer.enqueueResponse("", 500)

        val response = subject.fetchCardDetails("00000000")

        MatcherAssert.assertThat(response, CoreMatchers.`is`(CoreMatchers.equalTo(null)))

    }
}