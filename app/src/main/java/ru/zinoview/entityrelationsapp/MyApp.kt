package ru.zinoview.entityrelationsapp

import android.app.Application

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MyDatabase.init(this)
    }
}