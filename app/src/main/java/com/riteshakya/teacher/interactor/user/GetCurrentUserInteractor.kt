package com.riteshakya.teacher.interactor.user

import com.riteshakya.core.model.BaseUser
import com.riteshakya.teacher.repository.auth.AuthRepository
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