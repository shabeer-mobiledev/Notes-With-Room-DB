package com.shabeer.notesroomdb

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyBase : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}