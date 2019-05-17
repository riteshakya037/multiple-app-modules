package com.riteshakya.teacher.repository.auth

import io.reactivex.Single

interface AuthRepository {
    fun createAuth(phoneNo: String, school: String, password: String): Single<String>
}