package com.riteshakya.businesslogic.interactor.signup

import com.riteshakya.businesslogic.repository.student.StudentRepository
import com.riteshakya.businesslogic.repository.student.model.StudentModel
import io.reactivex.Completable
import javax.inject.Inject

class SignUpStudentInteractor
@Inject constructor(
        private val repository: StudentRepository
) {
    operator fun invoke(
            student: StudentModel
    ): Completable {
        return repository.createStudent(student)
    }
}