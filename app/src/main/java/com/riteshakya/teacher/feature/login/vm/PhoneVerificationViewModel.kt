package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.businesslogic.repository.auth.PhoneRepository
import com.riteshakya.businesslogic.repository.auth.PhoneRepository.Companion.NONE
import com.riteshakya.businesslogic.repository.auth.PhoneRepository.Companion.PHONE
import com.riteshakya.businesslogic.repository.auth.PhoneRepository.Companion.VERIFIED
import com.riteshakya.businesslogic.repository.auth.PhoneRepository.Companion.WAITING_CODE
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Singleton

@Singleton
class PhoneVerificationViewModel(
        private val phoneRepository: PhoneRepository
) : BaseViewModel() {

    val currentMode = MutableLiveData<@PhoneRepository.Companion.Mode Int>().also {
        it.value = NONE
    }

    val phoneNo = MutableLiveData<PhoneModel>()
    val code = MutableLiveData<String>()

    val isVerified = MutableLiveData<Boolean>().also {
        it.value = false
    }

    val verifiedObservable: Observable<Boolean> = Observable.create { observer ->
        isVerified.observeForever {
            observer.onNext(it)
        }
    }

    fun requestOrSubmitCode(): Completable {
        return if (currentMode.value == WAITING_CODE) {
            verifyCode()
        } else {
            requestCode()
        }
    }

    private fun verifyCode(): Completable {
        return phoneRepository.submitCode(code.value!!).doOnSuccess {
            currentMode.value = it
            isVerified.value = it == VERIFIED
        }.ignoreElement()
    }

    private fun requestCode(): Completable {
        return phoneRepository.requestCode(phoneNo.value!!.fullNumber).doOnSuccess {
            currentMode.value = it
            isVerified.value = it == VERIFIED
        }.ignoreElement()
    }

    fun enableRequestCode(it: Boolean?) {
        currentMode.value = if (it == true) PHONE else NONE
    }

    fun resendCode(): Completable {
        return phoneRepository.resendToken(phoneNo.value!!.fullNumber).doOnSuccess {
            currentMode.value = it
            isVerified.value = it == VERIFIED
        }.ignoreElement()
    }
}