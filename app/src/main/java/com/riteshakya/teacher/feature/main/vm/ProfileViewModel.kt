package com.riteshakya.teacher.feature.main.vm

import com.riteshakya.core.platform.BaseViewModel
import com.riteshakya.teacher.interactor.login.LogoutUserInteractor
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