package com.riteshakya.businesslogic.data.model

import com.riteshakya.core.model.Role
import java.util.*

open class BaseUserDto(
        @Role var role: String,
        val created_date: Date = Date(),
        val updated_date: Date = Date()
)