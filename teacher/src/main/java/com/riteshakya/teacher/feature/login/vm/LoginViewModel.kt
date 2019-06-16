package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.businesslogic.interactor.login.LoginUserInteractor
import com.riteshakya.businesslogic.interactor.login.LogoutUserInteractor
import com.riteshakya.businesslogic.interactor.school.GetSchoolsInteractor
import com.riteshakya.businesslogic.interactor.user.GetCurrentUserInteractor
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import javax.inject.Singleton

@Singleton
class LoginViewModel(
        val loginUserInteractor: LoginUserInteractor,
        val getSchoolInteractor: GetSchoolsInteractor,
        val getCurrentUserInteractor: GetCurrentUserInteractor,
        val logoutUserInteractor: LogoutUserInteractor,
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

    val phoneNo = MutableLiveData<PhoneModel>()
    val password = MutableLiveData<String>()
    val school = MutableLiveData<String>()

    private val schoolsSubject by lazy {
        PublishSubject.create<Boolean>()
    }

    val schools = schoolsSubject
            .startWith(true)
            .flatMapSingle { getSchoolInteractor() }
            .replay()
            .autoConnect(1)

    fun loginUser(): Completable {
        return loginUserInteractor(
                phoneNo.value!!.fullNumber,
                school.value ?: "",
                password.value ?: ""
        )
    }

    fun getCurrentUser(): Single<BaseUser> {
        return getCurrentUserInteractor()
    }

    fun logout(): Completable {
        return logoutUserInteractor()
    }

}