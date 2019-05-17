package com.riteshakya.teacher.repository.user

import com.riteshakya.core.model.BaseUser
import com.riteshakya.teacher.data.model.BaseUserDto
import com.riteshakya.teacher.repository.school.SchoolRepository
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun createUser(userId: String, user: BaseUserDto): Completable
    fun getUser(userId: String): Single<BaseUser>
}