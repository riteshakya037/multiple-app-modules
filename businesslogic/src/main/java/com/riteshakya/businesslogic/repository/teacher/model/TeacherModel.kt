package com.riteshakya.businesslogic.repository.teacher.model

import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.model.TEACHER

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