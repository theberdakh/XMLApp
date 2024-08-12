package com.theberdakh.xmlapp.core.network.source

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.theberdakh.xmlapp.core.network.result.AnotherError
import com.theberdakh.xmlapp.core.network.result.ResultModel
import retrofit2.Response

abstract class BaseDataSource {

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    protected suspend fun <T> invokeRequest(request: suspend () -> Response<T>): ResultModel<T> {
        return try {
            val response = request()
            if (response.isSuccessful)
                ResultModel.success(response.body())
            else ResultModel.error(
                AnotherError(
                    response.message(),
                    response.code()
                )
            )
        } catch (httpException: HttpException){
            ResultModel.error(httpException)
        } catch (exception: Exception) {
            ResultModel.error(exception)
        }
    }
}

