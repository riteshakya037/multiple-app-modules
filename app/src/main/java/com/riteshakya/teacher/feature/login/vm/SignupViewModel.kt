package com.riteshakya.teacher.feature.login.vm

import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.platform.BaseViewModel
import javax.inject.Singleton

@Singleton
class SignupViewModel(
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {

}