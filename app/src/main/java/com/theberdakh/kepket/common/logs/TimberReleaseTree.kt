package com.theberdakh.kepket.common.logs

import timber.log.Timber

class TimberReleaseTree: Timber.DebugTree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        //if you want to log in release builds
    }
}
