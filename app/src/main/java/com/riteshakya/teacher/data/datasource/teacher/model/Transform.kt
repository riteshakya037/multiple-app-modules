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