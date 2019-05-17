package com.riteshakya.core.model

import java.util.*


open class BaseUser(
    @Role var role: String,
    val created_date: Date = Date(),
    val updated_date: Date = Date()
)