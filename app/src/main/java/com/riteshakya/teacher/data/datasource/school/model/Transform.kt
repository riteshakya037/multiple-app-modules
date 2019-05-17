package com.riteshakya.teacher.data.datasource.school.model

import com.riteshakya.teacher.data.model.transform
import com.riteshakya.teacher.repository.school.model.SchoolModel

fun SchoolModel.transform(): SchoolDto = SchoolDto(
        nameOfAuthority,
        schoolName,
        schoolEmail,
        schoolArea,
        schoolCity,
        phoneNo.transform(),
        profilePhoto,
        schoolLogo
)

fun SchoolDto.transform(userId: String) = SchoolModel(
        name_of_authority,
        name,
        email,
        area,
        city,
        phone_no.transform(),
        profile_photo,
        logo,
        "",
        userId
)