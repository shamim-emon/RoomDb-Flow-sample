package com.example.roomdb_flow_sample

import android.app.Application

class MyApp : Application() {
    val database by lazy { AppDatabase.getInstance(this) }
    val repository by lazy { XRepository(database.xDao()) }

    override fun onCreate() {
        super.onCreate()
    }
}