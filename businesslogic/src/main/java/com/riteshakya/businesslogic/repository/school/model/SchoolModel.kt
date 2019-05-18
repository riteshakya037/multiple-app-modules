package com.riteshakya.businesslogic.repository.school.model

import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.model.SCHOOL

class SchoolModel(
        var nameOfAuthority: String = "",
        var schoolName: String = "",
        var schoolEmail: String = "",
        var schoolArea: String = "",
        var schoolCity: String = "",
        var phoneNo: PhoneModel = PhoneModel(),
        var profilePhoto: String = "",
        var schoolLogo: String = "",
        var password: String = "",
        var id: String = ""
) : BaseUser(SCHOOL)