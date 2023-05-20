package com.theelitedevelopers.homeofmovies

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HomeOfMoviesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}