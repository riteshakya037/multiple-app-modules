package com.riteshakya.student.feature.main.vm

import com.riteshakya.businesslogic.interactor.user.GetCurrentUserInteractor
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class HomeViewModel(
        val getCurrentUserInteractor: GetCurrentUserInteractor
) : BaseViewModel() {

    fun getCurrentUser(): Single<BaseUser> {
        return getCurrentUserInteractor()
    }
}