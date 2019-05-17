package com.riteshakya.teacher.data.datasource.teacher.model

import com.riteshakya.teacher.data.model.transform
import com.riteshakya.teacher.repository.teacher.model.TeacherModel

fun TeacherModel.transform(): TeacherDto = TeacherDto(
        firstName,
        lastName,
        school,
        isTeacher,
        className,
        section,
        phoneNo.transform(),
        profilePicture
)

fun TeacherDto.transform(): TeacherModel = TeacherModel(
        first_name,
        last_name,
        school,
        is_teacher,
        class_name,
        section,
        phone_no.transform(),
        profile_picture
)

fun TeacherDto.transform(userId: String, teacherId: String) = TeacherCollectionDto(
        school,
        userId,
        teacherId
)