package com.riteshakya.businesslogic.interactor.school

import com.riteshakya.businesslogic.repository.school.SchoolRepository
import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import io.reactivex.Single
import javax.inject.Inject

class GetSchoolsInteractor
@Inject constructor(
        private val repository: SchoolRepository
) {
    operator fun invoke(
    ): Single<List<SchoolModel>> {
        return repository.getSchoolList()
    }
}