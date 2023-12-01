package com.example.electrigo.utils

    sealed class ApiResult {
        object Loading : ApiResult()
        class Failure(val t: Throwable? = null) : ApiResult()
        class Success(val data: Any) : ApiResult()
        object Empty : ApiResult()
    }
