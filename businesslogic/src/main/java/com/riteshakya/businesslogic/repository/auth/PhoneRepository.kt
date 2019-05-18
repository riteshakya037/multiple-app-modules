package com.riteshakya.businesslogic.repository.auth

import androidx.annotation.IntDef
import io.reactivex.Single

interface PhoneRepository {
    fun requestCode(phoneNumber: String): Single<Int>
    fun submitCode(code: String): Single<Int>

    companion object {

        @IntDef(NONE, PHONE, WAITING_CODE, VERIFIED)
        annotation class Mode

        const val NONE = 0
        const val PHONE = 1
        const val WAITING_CODE = 2
        const val VERIFIED = 3
    }

    fun resendToken(phoneNumber: String): Single<Int>
}