package com.seamfix.printful.util

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.seamfix.mint.util.asset
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

class TestDispatcher: Dispatcher() {

    private val context: Context = InstrumentationRegistry.getInstrumentation().context

    override fun dispatch(request: RecordedRequest): MockResponse {
        val errorResponse = MockResponse().setResponseCode(404)

       // val pathWithoutQueryParams = Uri.parse(request?.path).path ?: return errorResponse
        val responseFile = "success.json"

        return if (responseFile != null) {
            val responseBody = asset(context, responseFile)
            MockResponse().setResponseCode(200).setBody(responseBody)
        } else {
            errorResponse
        }
    }
}