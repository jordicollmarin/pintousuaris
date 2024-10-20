package com.example.pintousuaris

import android.app.Application
import com.example.pintousuaris.data.AppContainer
import com.example.pintousuaris.data.DefaultAppContainer

class PintoUsuarisApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
