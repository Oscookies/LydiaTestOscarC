package com.example.lydiatestoscarc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class LydiaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}