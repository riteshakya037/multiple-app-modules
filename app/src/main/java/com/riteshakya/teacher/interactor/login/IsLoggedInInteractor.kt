package com.riteshakya.teacher.interactor.login

import com.riteshakya.teacher.repository.auth.AuthRepository
import javax.inject.Inject

class IsLoggedInInteractor
@Inject constructor(
        private val repository: AuthRepository
) {
    operator fun invoke(
    ): Boolean {
        return repository.isLoggedIn()
    }
}