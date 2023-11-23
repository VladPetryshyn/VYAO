package com.vladpetryshyn.vyao

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VyaoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}