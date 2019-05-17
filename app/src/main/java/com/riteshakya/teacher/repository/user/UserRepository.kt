package com.riteshakya.teacher.repository.user

import com.riteshakya.core.model.BaseUser
import com.riteshakya.teacher.data.datasource.school.model.SchoolDto
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun createUser(userId: String, user: BaseUser): Completable
    fun getSchoolUser(userId: String): Single<SchoolDto>
}