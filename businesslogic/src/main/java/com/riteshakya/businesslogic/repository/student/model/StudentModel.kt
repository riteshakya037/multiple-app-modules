package com.riteshakya.businesslogic.repository.student.model

import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.model.STUDENT

class StudentModel(
        val firstName: String = "",
        val lastName: String = "",
        val school: String = "",
        @Gender val gender: String = MALE,
        val className: String = "",
        val section: String = "",
        val phoneNo: PhoneModel = PhoneModel(),
        val profilePicture: String = "",
        val password: String = "",
        val id: String = ""
) : BaseUser(STUDENT) {
    lateinit var schoolModel: SchoolModel
}
