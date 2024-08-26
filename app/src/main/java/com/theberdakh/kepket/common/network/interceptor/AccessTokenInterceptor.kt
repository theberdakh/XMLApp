package com.theberdakh.kepket.common.network.interceptor

import com.theberdakh.kepket.common.preferences.SharedPreferencesStorage
import okhttp3.Interceptor
import okhttp3.Response

class AccessTokenInterceptor(private val sharedPreferencesStorage: SharedPreferencesStorage) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${sharedPreferencesStorage.token}"
            ).build()
    )
}
