package com.riteshakya.teacher.repository.teacher

import com.riteshakya.teacher.repository.teacher.model.TeacherModel
import io.reactivex.Completable

interface TeacherRepository {
    fun createTeacher(teacher: TeacherModel): Completable
}