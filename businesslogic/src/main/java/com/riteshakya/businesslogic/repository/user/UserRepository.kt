package com.riteshakya.businesslogic.repository.user

import com.riteshakya.core.model.BaseUser
import com.riteshakya.businesslogic.data.model.BaseUserDto
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun createUser(userId: String, user: BaseUserDto): Completable
    fun getUser(userId: String): Single<BaseUser>
}