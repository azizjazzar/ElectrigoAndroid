package com.example.electrigo.utils

sealed class ApiResult {
    object Loading : ApiResult()
    class Failure(val t: Throwable? = null, val message: String = t?.message ?: "Unknown Error") : ApiResult()
    class Success(val data: Any) : ApiResult()
    object Empty : ApiResult()
}
