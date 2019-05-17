package com.riteshakya.teacher.repository.teacher.model

import com.riteshakya.core.model.PhoneModel

data class TeacherModel(
    val firstName: String,
    val lastName: String,
    val school: String,
    val isTeacher: Boolean,
    val className: String,
    val section: String,
    val phoneNo: PhoneModel,
    val profilePicture: String,
    val password: String,
    val id: String = ""
)