package com.riteshakya.businesslogic.interactor.login

import com.riteshakya.businesslogic.repository.auth.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class LoginUserInteractor
@Inject constructor(
        private val repository: AuthRepository
) {
    operator fun invoke(
            phoneNo: String, school: String, password: String
    ): Completable {
        return repository.loginUser(phoneNo, school, password)
    }
}