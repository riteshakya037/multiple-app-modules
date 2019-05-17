package com.riteshakya.teacher.repository.school.model

import com.riteshakya.core.model.PhoneModel

class SchoolModel(
    var nameOfAuthority: String,
    var schoolName: String,
    var schoolEmail: String,
    var schoolArea: String,
    var schoolCity: String,
    var phoneNo: PhoneModel,
    var profilePhoto: String,
    var schoolLogo: String,
    var password: String,
    var id: String = ""
)