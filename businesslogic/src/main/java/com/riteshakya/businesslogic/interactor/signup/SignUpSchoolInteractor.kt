package com.riteshakya.businesslogic.interactor.signup

import com.riteshakya.businesslogic.repository.school.SchoolRepository
import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import io.reactivex.Completable
import javax.inject.Inject

class SignUpSchoolInteractor
@Inject constructor(
        private val repository: SchoolRepository
) {
    operator fun invoke(
            school: SchoolModel
    ): Completable {
        return repository.createSchool(school)
    }
}