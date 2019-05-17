package com.riteshakya.core.model

import androidx.annotation.StringDef

@StringDef(STUDENT, TEACHER, SCHOOL)
annotation class Role

const val STUDENT = "Student"
const val TEACHER = "Teacher"
const val SCHOOL = "School"