package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.platform.BaseViewModel

class SignUpViewModel(
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    val currentMode = MutableLiveData<Mode>()

    init {
        currentMode.value = Mode.TEACHER
    }


    enum class Mode(val mode: Int) {
        TEACHER(0), SCHOOL(1);

        companion object {
            @JvmStatic
            fun getMode(position: Int): Mode {
                for (f in values()) {
                    if (f.mode == position) return f
                }
                return TEACHER
            }
        }
    }


    fun signUpUser() {

    }
}