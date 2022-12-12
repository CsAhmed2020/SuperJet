package com.example.superjet.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        context =this
    }

    companion object {
        lateinit var context: MyApplication
        private set
    }
}