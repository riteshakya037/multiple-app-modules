package com.riteshakya.teacher.feature.login.vm

import androidx.annotation.IntDef
import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class PhoneVerificationViewModel(
    val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

    val currentMode = MutableLiveData<@Mode Int>().also {
        it.value = NONE
    }

    val phoneNo = MutableLiveData<PhoneModel>()
    val code = MutableLiveData<String>()

    private val isVerified = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val verifiedObservable: Observable<Boolean> = Observable.create { observer ->
        isVerified.observeForever {
            observer.onNext(it)
        }
    }

    fun requestOrSubmitCode() {
        if (currentMode.value == WAITING_CODE) {
            verifyCode()
        } else {
            requestCode()
        }
    }

    private fun verifyCode() {
        currentMode.value = VERIFIED
        isVerified.value = true
    }

    private fun requestCode() {
        currentMode.value = WAITING_CODE
        isVerified.value = false
    }

    fun enableRequestCode(it: Boolean?) {
        currentMode.value = if (it == true) PHONE else NONE
    }

    fun resendCode() {

    }

    companion object {

        @IntDef(NONE, PHONE, WAITING_CODE, VERIFIED)
        annotation class Mode

        const val NONE = 0
        const val PHONE = 1
        const val WAITING_CODE = 2
        const val VERIFIED = 3
    }
}