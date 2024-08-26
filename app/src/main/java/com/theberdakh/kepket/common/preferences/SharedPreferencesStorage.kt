package com.theberdakh.kepket.common.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesStorage(context: Context) {

    private val sharedPreferences by lazy(LazyThreadSafetyMode.NONE) {context.getSharedPreferences(
        SHARED_PREFERENCES_NAME, SHARED_PREFERENCES_MODE
    ) }

    fun getSharedPreferencesStorage(): SharedPreferences {
        return sharedPreferences
    }

    /**
    * Add share prefs here
    * @sample
     * ```
     *     var token by StringPreference(sharedPreferences)
     * ```
     *
     * */
    var token by StringPreference(sharedPreferences)

    var isLoggedIn by BooleanPreference(sharedPreferences, false)

    var login by StringPreference(sharedPreferences)

    var fullName by StringPreference(sharedPreferences)

    var streamKey by StringPreference(sharedPreferences)

    var twitchChannelUsername by StringPreference(sharedPreferences)

    var type by StringPreference(sharedPreferences)

}
