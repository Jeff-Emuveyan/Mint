package com.seamfix.mint.ui.main.result

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seamfix.mint.R
import com.seamfix.mint.model.CardDetailsResponse
import kotlinx.android.synthetic.main.bottom_sheet.*

class ResultBottomSheet(var cardDetailsResponse: CardDetailsResponse) : BottomSheetDialogFragment() {

    companion object{
        /***  A static variable that will be true if any instance of this class is visible ***/
        var isSheetOpen:Boolean = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //needed to remove the white background color of the bottom sheet.
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isSheetOpen = true
        setUpUI(cardDetailsResponse)
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isSheetOpen = false
    }


    /*** Populates the UI with data from the response object  ***/
    private fun setUpUI(cardDetailsResponse: CardDetailsResponse){
        tvBrand.text = cardDetailsResponse.brand
        tvBank.text = cardDetailsResponse.bank?.name ?: getString(R.string.no_found)
        tvCountry.text = cardDetailsResponse.country?.name ?: getString(R.string.no_found)
        tvType.text = cardDetailsResponse.type ?: getString(R.string.no_found)
    }
}