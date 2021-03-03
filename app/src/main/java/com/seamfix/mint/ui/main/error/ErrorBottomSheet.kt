package com.seamfix.mint.ui.main.error

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seamfix.mint.R
import kotlinx.android.synthetic.main.error_bottom_sheet.*

class ErrorBottomSheet(var message: String?): BottomSheetDialogFragment() {

    companion object{
        /***  A static variable that will be true if any instance of this class is visible ***/
        var isSheetOpen:Boolean = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.error_bottom_sheet, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //needed to remove the white background color of the bottom sheet.
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isSheetOpen = true
        tvMessage.text = message ?: getString(R.string.error_message)
    }


    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isSheetOpen = false
    }
}