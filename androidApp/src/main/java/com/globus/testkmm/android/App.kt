package com.globus.testkmm.android

import android.app.Application
import com.globus.testkmm.di.androidModule
import com.globus.testkmm.di.DiApp

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        DiApp.initModules(androidModule)
    }
}