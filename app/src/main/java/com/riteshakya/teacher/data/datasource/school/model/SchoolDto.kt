package com.riteshakya.teacher.data.datasource.school.model

import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.SCHOOL
import com.riteshakya.teacher.data.model.PhoneDto

class SchoolDto(
    var name_of_authority: String = "",
    var name: String = "",
    var email: String = "",
    var area: String = "",
    var city: String = "",
    var phone_no: PhoneDto = PhoneDto(),
    var profile_photo: String = "",
    var logo: String = ""
) : BaseUser(SCHOOL) {
}