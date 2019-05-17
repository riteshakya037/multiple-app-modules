package com.riteshakya.teacher.data.datasource.teacher.model

import com.riteshakya.core.model.TEACHER
import com.riteshakya.teacher.data.model.BaseUserDto
import com.riteshakya.teacher.data.model.PhoneDto

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