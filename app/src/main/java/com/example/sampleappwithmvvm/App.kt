package com.example.sampleappwithmvvm

import android.app.Application
import com.example.sampleappwithmvvm.di.koinModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        startKoin {
            // TODO: Change logger based on build
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(
                listOf(
                    koinModule
                )
            )
        }

        super.onCreate()
    }
}