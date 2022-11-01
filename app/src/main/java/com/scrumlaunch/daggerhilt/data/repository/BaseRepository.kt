package com.scrumlaunch.daggerhilt.data.repository

import android.content.Context
import com.google.gson.Gson
import com.scrumlaunch.daggerhilt.R
import com.scrumlaunch.daggerhilt.domain.model.BaseResponse
import com.scrumlaunch.daggerhilt.util.ErrorResponse
import com.scrumlaunch.daggerhilt.util.ResultWrapper
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

open class BaseRepository(context: Context) {

    private val GENERAL_ERROR_MESSAGE = context.getString(R.string.general_error_message)
    private val NETWORK_ERROR_MESSAGE = context.getString(R.string.network_error_message)


    suspend fun <T : BaseResponse> apiCallInternal(
        apiCall: suspend () -> Response<T>?
    ): ResultWrapper<Response<T>> {
        return try {
            val result = apiCall()
            if (result?.isSuccessful == true) {
                ResultWrapper.Success(result)
            } else {
                val errorBody = Gson().fromJson(
                    result?.errorBody()?.string(), ErrorResponse::class.java
                ).let {
                    if (it.status == null && result?.code() == 401) {
                        it.copy(code = 401)
                    } else {
                        it
                    }
                }
                ResultWrapper.Error(errorBody)
            }
        } catch (throwable: Throwable) {
            val networkError = ErrorResponse(message = NETWORK_ERROR_MESSAGE)
            throwable.printStackTrace()
            when (throwable) {
                is SocketTimeoutException -> ResultWrapper.Error(
                    ErrorResponse(
                        message = GENERAL_ERROR_MESSAGE,
                        code = 522
                    )
                )
                is IOException -> ResultWrapper.Error(networkError)
                is HttpException -> ResultWrapper.Error(ErrorResponse(message = GENERAL_ERROR_MESSAGE))
                else -> {
                    ResultWrapper.Error(ErrorResponse(message = GENERAL_ERROR_MESSAGE))
                }
            }
        }
    }


    suspend inline fun <reified T : BaseResponse> handleNetwork(
        crossinline apiCall: suspend () -> Response<T>
    ): ResultWrapper<Response<T>> {
        val wrapper = apiCallInternal {
            apiCall()
        }
        return wrapper
    }


}