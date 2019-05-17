package com.riteshakya.teacher.interactor.signup

import com.riteshakya.teacher.repository.teacher.TeacherRepository
import com.riteshakya.teacher.repository.teacher.model.TeacherModel
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