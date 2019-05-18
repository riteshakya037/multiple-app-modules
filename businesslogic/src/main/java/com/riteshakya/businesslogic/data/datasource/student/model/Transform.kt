package com.riteshakya.businesslogic.data.datasource.student.model

import com.riteshakya.businesslogic.data.model.transform
import com.riteshakya.businesslogic.repository.student.model.StudentModel

fun StudentModel.transform(): StudentDto = StudentDto(
    firstName,
    lastName,
    school,
    gender,
    className,
    section,
    phoneNo.transform(),
    profilePicture
)

fun StudentDto.transform(): StudentModel = StudentModel(
    first_name,
    last_name,
    school,
    gender,
    class_name,
    section,
    phone_no.transform(),
    profile_picture
)

fun StudentDto.transform(userId: String, teacherId: String) = StudentCollectionDto(
    school,
    userId,
    teacherId
)