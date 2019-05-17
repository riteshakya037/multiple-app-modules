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

fun SchoolDto.transform() = SchoolModel(
        name_of_authority,
        name,
        email,
        area,
        city,
        phone_no.transform(),
        profile_photo,
        logo
)

fun SchoolDto.transform(
        userId: String, schoolId: String
): SchoolCollectionDto = SchoolCollectionDto(
        userId,
        schoolId,
        name,
        logo
)

fun SchoolCollectionDto.transform() = SchoolModel(
        schoolName = name,
        schoolLogo = logo,
        id = school_id
)