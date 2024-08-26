package com.theberdakh.kepket.common.app

import android.app.Application
import com.theberdakh.kepket.BuildConfig
import com.theberdakh.kepket.common.logs.TimberDebugTree
import com.theberdakh.kepket.common.logs.TimberReleaseTree
import timber.log.Timber

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        initLogs()

        Timber.i("onCreate()")
        
    }

    private fun initLogs() {
        if (BuildConfig.DEBUG){
            Timber.plant(TimberDebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }
    }
}
