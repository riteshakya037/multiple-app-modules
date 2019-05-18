package com.riteshakya.core.exception

class StatusResponseException(val errorMessage: String) : RuntimeException(errorMessage)