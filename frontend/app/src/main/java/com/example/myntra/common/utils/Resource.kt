package com.example.myntra.common.utils

import okhttp3.ResponseBody

sealed class Resource<T>(
    val data: T? = null,
    val errorBody: ResponseBody? = null,
    val message: String? = null,
) {

    class Success<T>(
        data: T? = null,
        errorBody: ResponseBody? = null,
    ) : Resource<T>(
        data, errorBody
    )

    class Loading<T>(
        data: T? = null,
        errorBody: ResponseBody? = null,
    ) : Resource<T>(
        data, errorBody
    )

    class Error<T>(
        data: T? = null,
        message: String? = null,
        errorBody: ResponseBody? = null,
    ) : Resource<T>(
        data, message = message, errorBody = errorBody
    )

}