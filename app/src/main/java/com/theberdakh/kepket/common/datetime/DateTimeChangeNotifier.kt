package com.theberdakh.kepket.common.datetime

import androidx.annotation.UiThread
import java.time.LocalDateTime

interface DateTimeChangeNotifier {

    interface Listener {
        /**
         * will be called once a second with the current LocalDateTime
         */
        @UiThread
        fun onLocalDateTimeChanged(currentLocalDateTime: LocalDateTime)
    }

}
