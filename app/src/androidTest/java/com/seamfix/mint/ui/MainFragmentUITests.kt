package com.seamfix.mint.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jakewharton.espresso.OkHttp3IdlingResource
import com.seamfix.mint.R
import com.seamfix.mint.data.repo.CardRepository
import com.seamfix.mint.data.source.remote.Service
import com.seamfix.mint.ui.main.MainActivity
import com.seamfix.mint.util.enqueueResponse
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainFragmentUITests {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, true, false)



    /*** This UI tests ensures that the 'proceed' button is disabled by default ***/
    @Test
    fun proceedButtonIsDisabledOnAppLunch() {
        activityRule.launchActivity(null)
        onView(withId(R.id.processButton)).check(matches(not(isEnabled())))
    }


    /*** This UI test ensures that the 'proceed' button is enabled only when the user has inputted
     * a valid card number
     */
    @Test
    fun proceedButtonIsEnabled() {
        activityRule.launchActivity(null)

        //the button should be disabled initially:
        onView(withId(R.id.processButton)).check(matches(not(isEnabled())))

        //type in a valid card number
        var validCardNumber = "4084084084084081"
        onView(withId(R.id.editText)).perform(typeText(validCardNumber))

        onView(withId(R.id.processButton)).check(matches(isEnabled()))
    }


    /*** This test checks proper error message is when network call fails */
    @Test
    fun  showDisplayErrorView(){
        activityRule.launchActivity(null)

        //close the  keyboard:
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())

        //check that the progressbar is hidden initially:
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))

        //the button should be disabled initially:
        onView(withId(R.id.processButton)).check(matches(not(isEnabled())))

        //type in a valid card number
        val validCardNumber = "4084084084084081"
        onView(withId(R.id.editText)).perform(typeText(validCardNumber))

        //close the  keyboard:
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard())

        //the button should be enabled
        onView(withId(R.id.processButton)).check(matches(isEnabled()))

        //click on the  proceed  button:
        onView(withId(R.id.processButton)).perform(click())

        //check the the error message showed:
        onView(withId(R.id.tvMessage))
            .check(matches(withText("Network error. Unable to fetch card details, try again.")))
    }

}