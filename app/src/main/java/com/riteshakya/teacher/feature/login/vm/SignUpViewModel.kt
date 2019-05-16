package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.platform.BaseViewModel
import com.riteshakya.ui.components.PhoneInput

class SignUpViewModel(
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    val currentMode = MutableLiveData<Mode>().also {
        it.value = Mode.TEACHER
    }

    // Password Screen
    val password = MutableLiveData<String>()

    // SignUp teacher screen
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val school = MutableLiveData<String>()
    val teacher = MutableLiveData<Boolean>()
    val classValue = MutableLiveData<String>()
    val sectionValue = MutableLiveData<String>()

    // SignUp school screen
    val nameOfAuthority = MutableLiveData<String>()
    val schoolName = MutableLiveData<String>()
    val schoolEmail = MutableLiveData<String>()
    val schoolAreaCode = MutableLiveData<String>()
    val schoolCity = MutableLiveData<String>()

    // Phone
    val phoneNo = MutableLiveData<PhoneInput.PhoneModel>()

    // Profile Photo
    val profilePhoto = MutableLiveData<String>()

    // School logo
    val schoolLogo = MutableLiveData<String>()

    fun signUpUser() {
        when (currentMode.value) {
            Mode.TEACHER -> signUpTeacher()
            Mode.SCHOOL -> signUpSchool()
            else -> {

            }
        }

    }

    private fun signUpSchool() {

    }

    private fun signUpTeacher() {

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
}