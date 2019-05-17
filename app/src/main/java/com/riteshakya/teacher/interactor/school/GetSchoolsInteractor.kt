package com.riteshakya.teacher.interactor.school

import com.riteshakya.teacher.repository.school.SchoolRepository
import com.riteshakya.teacher.repository.school.model.SchoolModel
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