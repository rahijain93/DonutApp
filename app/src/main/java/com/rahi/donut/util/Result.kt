package com.rahi.donut.util

sealed class Result<out T> {
    data class Success(val msg:String) : Result<Nothing>()
    data class Loading(val status: Boolean) : Result<Nothing>()
    sealed class Error(val exception:Exception) : Result<Nothing>() {
        class RecoverableError(exception: Exception) : Error(exception)
        class NonRecoverableError(exception: Exception) : Error(exception)
    }
}




