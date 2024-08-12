package com.theberdakh.xmlapp.core.logs

import timber.log.Timber

class TimberDebugTree: Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        val stackElement = Throwable().stackTrace
            .first{
                !it.className.contains("Timber") && !it.className.contains(Logger.javaClass.simpleName)
            }
        return super.createStackElementTag(stackElement)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        super.log(priority, tag, "[${Thread.currentThread().name}] $message", t)
    }
}
