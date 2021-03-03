package com.seamfix.mint

class TestApplication: MainApplication() {

    override val baseUrl: String
        get() = "http://127.0.0.1:8080"
}