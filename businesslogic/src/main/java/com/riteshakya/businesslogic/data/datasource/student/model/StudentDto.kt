package com.riteshakya.businesslogic.data.datasource.student.model

import com.riteshakya.businesslogic.data.model.BaseUserDto
import com.riteshakya.businesslogic.data.model.PhoneDto
import com.riteshakya.businesslogic.repository.student.model.MALE
import com.riteshakya.core.model.STUDENT

data class StudentDto(
    var first_name: String = "",
    var last_name: String = "",
    var school: String = "",
    var gender: String = MALE,
    var class_name: String = "",
    var section: String = "",
    var phone_no: PhoneDto = PhoneDto(),
    var profile_picture: String = ""
) : BaseUserDto(STUDENT)