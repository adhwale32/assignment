package com.bookmyshow.common_ui.model

import retrofit2.Response
import java.net.HttpURLConnection

open class DataSource {
    /**
     * Process the api call and handle the response
     */
    suspend fun <T> processResponse(webCall: suspend ()-> Response<T>): DataState<T> {
        try {
            val response = webCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return DataState.Success(body)
                }
                response.raw().let {
                    if (it.code == HttpURLConnection.HTTP_NO_CONTENT) {
                        return DataState.Success(it.code as T)
                    }
                }
            }
            return DataState.Error(errorHandler(response))
        } catch (e: Exception) {
            return DataState.Error(e.message!!)
        }
    }


    /**
     * DataState class - for api responses
     *
     */
    sealed class DataState<out R> {
        data class Success<out T>(val data: T): DataState<T>()
        data class Error(val error: String): DataState<Nothing>()
        data class Loader(val isLoading:Boolean): DataState<Nothing>()

    }

    /**
     * error handler
     */
    private fun <T> errorHandler(response: Response<T>) = when (response.code()) {
        HttpURLConnection.HTTP_INTERNAL_ERROR -> "Something went wrong"
        HttpURLConnection.HTTP_BAD_REQUEST -> "Bad request"
        HttpURLConnection.HTTP_FORBIDDEN -> "Forbidden error"
        HttpURLConnection.HTTP_NOT_FOUND -> "Resource not found"
        else -> "bad gateway"
    }
}