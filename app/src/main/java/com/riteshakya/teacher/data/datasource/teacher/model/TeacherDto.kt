package com.riteshakya.teacher.data.datasource.teacher.model

import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.TEACHER
import com.riteshakya.teacher.data.model.PhoneDto

class TeacherDto(
    var first_name: String,
    var last_name: String,
    var school: String,
    var is_teacher: Boolean,
    var class_name: String,
    var section: String,
    var phone_no: PhoneDto,
    var profile_picture: String
) : BaseUser(TEACHER)