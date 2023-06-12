package com.asolis.kotlinmvvmhilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MVVMHiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}