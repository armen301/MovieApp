package com.movie.app

import android.app.Application
import com.movie.app.network.APIInterface
import com.movie.app.network.APIService

class App : Application() {

    companion object {
        lateinit var apiService: APIInterface
    }

    override fun onCreate() {
        super.onCreate()
        apiService = APIService.getInstance()
    }
}