package com.riteshakya.businesslogic.data.datasource.school.model

import com.riteshakya.businesslogic.data.model.transform
import com.riteshakya.businesslogic.repository.school.model.SchoolModel

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