package com.riteshakya.businesslogic.data.datasource.school.model

import com.riteshakya.businesslogic.data.model.BaseUserDto
import com.riteshakya.businesslogic.data.model.PhoneDto
import com.riteshakya.core.model.SCHOOL

data class SchoolDto(
        var name_of_authority: String = "",
        var name: String = "",
        var email: String = "",
        var area: String = "",
        var city: String = "",
        var phone_no: PhoneDto = PhoneDto(),
        var profile_photo: String = "",
        var logo: String = ""
) : BaseUserDto(SCHOOL)