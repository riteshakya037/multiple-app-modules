package com.riteshakya.core.platform

sealed class ResultState(val failure: String = "") {
    open val isLoading = false
    open val isError = false
    val isSuccess
        get() = !isLoading && !isError

    object Loading : ResultState() {
        override val isLoading = true
    }

    object Success : ResultState()

    class Error(message: String) : ResultState(message) {
        override val isError = true
    }

    override fun toString(): String {
        return "ResultState(failure='$failure', isLoading=$isLoading, isError=$isError)"
    }


}