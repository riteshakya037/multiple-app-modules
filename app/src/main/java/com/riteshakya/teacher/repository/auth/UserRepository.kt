package com.riteshakya.teacher.repository.auth

import com.riteshakya.core.model.BaseUser
import io.reactivex.Completable
import io.reactivex.Single

interface AuthRepository {
    fun isLoggedIn(): Boolean
    fun logoutUser(): Completable
    fun createAuth(phoneNo: String, school: String, password: String): Single<String>
    fun loginUser(phoneNo: String, school: String, password: String): Completable
    fun getUser(): Single<BaseUser>
}