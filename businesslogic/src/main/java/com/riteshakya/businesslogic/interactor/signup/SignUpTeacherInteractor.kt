package com.riteshakya.businesslogic.interactor.signup

import com.riteshakya.businesslogic.repository.teacher.TeacherRepository
import com.riteshakya.businesslogic.repository.teacher.model.TeacherModel
import io.reactivex.Completable
import javax.inject.Inject

class SignUpTeacherInteractor
@Inject constructor(
        private val repository: TeacherRepository
) {
    operator fun invoke(
            teacher: TeacherModel
    ): Completable {
        return repository.createTeacher(teacher)
    }
}