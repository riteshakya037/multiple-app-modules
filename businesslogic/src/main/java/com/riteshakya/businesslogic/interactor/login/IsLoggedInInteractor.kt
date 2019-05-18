package com.riteshakya.businesslogic.interactor.login

import com.riteshakya.businesslogic.repository.auth.AuthRepository
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