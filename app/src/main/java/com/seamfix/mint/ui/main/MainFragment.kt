package com.seamfix.mint.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.seamfix.mint.R
import com.seamfix.mint.model.CardDetailsResponse
import com.seamfix.mint.ui.main.error.ErrorBottomSheet
import com.seamfix.mint.ui.main.result.ResultBottomSheet
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //dependency Inject the view model
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpUIState(UIState.DEFAULT)

        editText.doOnTextChanged { _, _, _, _ ->
            editText.error = null
        }

        editText.doAfterTextChanged {
            if(viewModel.validateCardNumber(editText.text.toString())){
                setUpUIState(UIState.VALID_CARD_NUMBER)
            }else{
                setUpUIState(UIState.INVALID_CARD_NUMBER)
            }
        }

        processButton.setOnClickListener {
            setUpUIState(UIState.LOADING)
            lifecycleScope.launch {
                fetchCardDetails()
            }
        }
    }

    /*** Initiates the request for the details of the card ****/
    private suspend fun fetchCardDetails() {
        val result = viewModel.fetchCardDetails(editText.text.toString())
        if(result == null){
            setUpUIState(UIState.NO_RESULT)
        }else{
            setUpUIState(UIState.RESULT_FOUND, result)
        }
    }

    /*** This method determines the UI state of the app ***/
    private fun setUpUIState(state: UIState,
                             cardDetailsResponse: CardDetailsResponse? = null,
                             errorMessage: String? = null){

        when(state){
            UIState.INVALID_CARD_NUMBER ->{
                editText.error = getString(R.string.invalid_number_error)
                processButton.visibility = View.VISIBLE
                processButton.isEnabled = false
                progressBar.visibility = View.INVISIBLE
            }

            UIState.VALID_CARD_NUMBER ->{
                processButton.visibility = View.VISIBLE
                processButton.isEnabled = true
                progressBar.visibility = View.INVISIBLE
            }

            UIState.LOADING ->{
                processButton.visibility = View.INVISIBLE
                processButton.isEnabled = false
                progressBar.visibility = View.VISIBLE
            }

            UIState.NO_RESULT ->{
                processButton.visibility = View.VISIBLE
                processButton.isEnabled = true
                progressBar.visibility = View.INVISIBLE
                //show the error message:
                ErrorBottomSheet(errorMessage)
                    .show(childFragmentManager, MainFragment::class.java.simpleName)
            }

            UIState.RESULT_FOUND ->{
                processButton.visibility = View.VISIBLE
                processButton.isEnabled = true
                progressBar.visibility = View.INVISIBLE
                //show the card details:
                if(cardDetailsResponse != null){
                    ResultBottomSheet(cardDetailsResponse)
                        .show(childFragmentManager, MainFragment::class.java.simpleName)
                }
            }

            UIState.DEFAULT ->{
                processButton.visibility = View.VISIBLE
                processButton.isEnabled = false
                progressBar.visibility = View.INVISIBLE
            }
        }
    }

    enum class UIState{
        INVALID_CARD_NUMBER,
        VALID_CARD_NUMBER,
        DEFAULT,
        LOADING,
        RESULT_FOUND,
        NO_RESULT
    }



}