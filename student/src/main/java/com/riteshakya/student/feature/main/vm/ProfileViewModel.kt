package com.riteshakya.student.feature.main.vm

import com.riteshakya.businesslogic.interactor.login.LogoutUserInteractor
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Completable
import javax.inject.Singleton

@Singleton
class ProfileViewModel(
        val logoutUserInteractor: LogoutUserInteractor
) : BaseViewModel() {

    fun logout(): Completable {
        return logoutUserInteractor()
    }
}