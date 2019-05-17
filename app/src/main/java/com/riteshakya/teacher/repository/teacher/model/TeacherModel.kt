package com.riteshakya.teacher.repository.teacher.model

import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.model.TEACHER
import com.riteshakya.teacher.repository.school.model.SchoolModel

data class TeacherModel(
        val firstName: String = "",
        val lastName: String = "",
        val school: String = "",
        val isTeacher: Boolean = false,
        val className: String = "",
        val section: String = "",
        val phoneNo: PhoneModel = PhoneModel(),
        val profilePicture: String = "",
        val password: String = "",
        val id: String = ""
) : BaseUser(TEACHER) {
    lateinit var schoolModel: SchoolModel
}