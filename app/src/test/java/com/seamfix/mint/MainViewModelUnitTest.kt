package com.seamfix.mint

import com.seamfix.mint.fakes.FakeCardRepository
import com.seamfix.mint.ui.main.MainViewModel
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class MainViewModelUnitTest {

    private val subject = MainViewModel(FakeCardRepository(null))

    @Test
    fun `can identify all possible valid and invalid card numbers`() {

        val validNumberA = "5060666666666666666" //valid card number
        assertThat(true, `is`(equalTo(subject.validateCardNumber(validNumberA))))

        val validNumberB = "5060666666666666666mmmmm" //valid card number because alphabets will be stripped off.
        assertThat(true, `is`(equalTo(subject.validateCardNumber(validNumberB))))

        val validNumberC = "DUMMMMYYYY" //Invalid
        assertThat(false, `is`(equalTo(subject.validateCardNumber(validNumberC))))

        val validNumberD = "0" //Invalid
        assertThat(false, `is`(equalTo(subject.validateCardNumber(validNumberD))))

        val validNumberE = "-111111" //Invalid
        assertThat(false, `is`(equalTo(subject.validateCardNumber(validNumberE))))

        val validNumberF = "5078 5078 5078 5078 12" //valid card number
        assertThat(true, `is`(equalTo(subject.validateCardNumber(validNumberF))))

        val validNumberG = "4084080000000409" //valid card number
        assertThat(true, `is`(equalTo(subject.validateCardNumber(validNumberG))))
    }
}