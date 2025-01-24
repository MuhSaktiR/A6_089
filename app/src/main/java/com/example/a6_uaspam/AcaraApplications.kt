package com.example.a6_uaspam

import android.app.Application
import com.example.a6_uaspam.dependenciesinjection.AcaraContainer
import com.example.a6_uaspam.dependenciesinjection.AppContainer

class AcaraApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AcaraContainer()
    }
}