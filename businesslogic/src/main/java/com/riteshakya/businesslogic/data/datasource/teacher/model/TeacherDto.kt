package com.riteshakya.businesslogic.data.datasource.teacher.model

import com.riteshakya.businesslogic.data.model.BaseUserDto
import com.riteshakya.businesslogic.data.model.PhoneDto
import com.riteshakya.core.model.TEACHER

data class TeacherDto(
        var first_name: String = "",
        var last_name: String = "",
        var school: String = "",
        var is_teacher: Boolean = false,
        var class_name: String = "",
        var section: String = "",
        var phone_no: PhoneDto = PhoneDto(),
        var profile_picture: String = ""
) : BaseUserDto(TEACHER)