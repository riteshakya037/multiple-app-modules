package com.riteshakya.teacher.interactor.login

import com.riteshakya.teacher.repository.auth.AuthRepository
import io.reactivex.Completable
import javax.inject.Inject

class LogoutUserInteractor
@Inject constructor(
        private val repository: AuthRepository
) {
    operator fun invoke(
    ): Completable {
        return repository.logoutUser()
    }
}