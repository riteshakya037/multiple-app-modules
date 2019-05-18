package com.riteshakya.businesslogic.repository.student.model

import androidx.annotation.StringDef

@StringDef(
        MALE,
        FEMALE
)
annotation class Gender

const val MALE = "Male"
const val FEMALE = "Female"