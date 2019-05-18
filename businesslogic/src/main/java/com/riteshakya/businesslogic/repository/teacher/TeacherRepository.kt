package com.riteshakya.businesslogic.repository.teacher

import com.riteshakya.businesslogic.repository.teacher.model.TeacherModel
import io.reactivex.Completable

interface TeacherRepository {
    fun createTeacher(teacher: TeacherModel): Completable
}