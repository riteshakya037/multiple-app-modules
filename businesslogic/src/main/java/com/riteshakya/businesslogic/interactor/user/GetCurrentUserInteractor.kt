package com.riteshakya.businesslogic.interactor.user

import com.riteshakya.businesslogic.repository.auth.AuthRepository
import com.riteshakya.core.model.BaseUser
import io.reactivex.Single
import javax.inject.Inject

class GetCurrentUserInteractor
@Inject constructor(
        private val repository: AuthRepository
) {
    operator fun invoke(
    ): Single<BaseUser> {
        return repository.getUser()
    }
}