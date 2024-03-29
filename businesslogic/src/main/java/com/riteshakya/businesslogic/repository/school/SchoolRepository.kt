package com.riteshakya.businesslogic.repository.school

import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import io.reactivex.Completable
import io.reactivex.Single

interface SchoolRepository {
    fun createSchool(school: SchoolModel): Completable
    fun getSchoolList(): Single<List<SchoolModel>>
    fun getSchool(schoolId: String): Single<SchoolModel>
}