package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.platform.BaseViewModel
import com.riteshakya.ui.components.PhoneInput
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class PhoneVerificationViewModel(
    val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

    val currentMode = MutableLiveData<Mode>().also {
        it.value = Mode.NONE
    }

    val phoneNo = MutableLiveData<PhoneInput.PhoneModel>()
    val code = MutableLiveData<String>()

    val isVerified = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val verifiedObservable: Observable<Boolean> = Observable.create { observer ->
        isVerified.observeForever {
            observer.onNext(it)
        }
    }

    fun requestOrSubmitCode() {
        if (currentMode.value == Mode.WAITING_CODE) {
            verifyCode()
        } else {
            requestCode()
        }
    }

    private fun verifyCode() {
        currentMode.value = Mode.VERIFIED
        isVerified.value = true
    }

    private fun requestCode() {
        currentMode.value = Mode.WAITING_CODE
        isVerified.value = false
    }

    fun enableRequestCode(it: Boolean?) {
        currentMode.value = if (it == true) Mode.PHONE else Mode.NONE
    }

    fun resendCode() {

    }


    enum class Mode(val mode: Int) {
        NONE(0), PHONE(1), WAITING_CODE(1), VERIFIED(3);

        companion object {
            @JvmStatic
            fun getMode(position: Int): Mode {
                for (f in values()) {
                    if (f.mode == position) return f
                }
                return NONE
            }
        }
    }
}