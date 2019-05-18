package com.riteshakya.businesslogic.repository.student

import com.riteshakya.businesslogic.repository.student.model.StudentModel
import io.reactivex.Completable

interface StudentRepository {
    fun createStudent(student: StudentModel): Completable
}