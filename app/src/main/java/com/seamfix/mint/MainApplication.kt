package com.seamfix.mint

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
open class MainApplication : Application(){

    open val baseUrl = "https://lookup.binlist.net/"
}