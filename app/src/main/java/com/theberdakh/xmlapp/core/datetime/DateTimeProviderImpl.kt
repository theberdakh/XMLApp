package com.theberdakh.xmlapp.core.datetime

import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.theberdakh.xmlapp.core.Observable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.Date

class DateTimeProviderImpl : Observable<DateTimeChangeNotifier.Listener>(), DateTimeProvider, DateTimeChangeNotifier {

    private val uiHandler = Handler(Looper.getMainLooper())

    private val listenersNotificationRunnable: Runnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            listeners.map { it.onLocalDateTimeChanged(getLocalDateTime()) }
            uiHandler.postDelayed(this, 1000)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocalDate(): LocalDate {
        return LocalDate.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocalTime(): LocalTime {
        return LocalTime.now()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLocalDateTime(): LocalDateTime {
        return LocalDateTime.of(getLocalDate(), getLocalTime())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDateUtc(): Date {
        return Date.from(getZonedDateTimeUtc().toInstant())
    }

    override fun getTimestampUtc(): Long {
        return System.currentTimeMillis()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getZonedDateTimeUtc(): ZonedDateTime {
        return ZonedDateTime.now(ZoneOffset.UTC)
    }

    override fun getNanoTime(): Long {
        return System.nanoTime()
    }

    override fun onFirstListenerRegistered() {
        super.onFirstListenerRegistered()
        uiHandler.post(listenersNotificationRunnable)
    }

    override fun onLastListenerUnregistered() {
        super.onLastListenerUnregistered()
        uiHandler.removeCallbacks(listenersNotificationRunnable)
    }

}
