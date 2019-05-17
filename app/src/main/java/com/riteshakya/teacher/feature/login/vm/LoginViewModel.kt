package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import timber.log.Timber
import javax.inject.Singleton

@Singleton
class LoginViewModel(
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    fun loginUser() {
        Timber.e("${phoneNo.value} ${password.value} ${school.value}")
    }

    val phoneNo = MutableLiveData<PhoneModel>()
    val password = MutableLiveData<String>()
    val school = MutableLiveData<String>()
}