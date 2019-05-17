package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import com.riteshakya.teacher.interactor.school.GetSchoolsInteractor
import com.riteshakya.teacher.interactor.signup.SignUpSchoolInteractor
import com.riteshakya.teacher.interactor.signup.SignUpTeacherInteractor
import com.riteshakya.teacher.repository.school.model.SchoolModel
import com.riteshakya.teacher.repository.teacher.model.TeacherModel
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class SignUpViewModel
@Inject constructor(
        val signUpSchoolInteractor: SignUpSchoolInteractor,
        val signUpTeacherInteractor: SignUpTeacherInteractor,
        val getSchoolInteractor: GetSchoolsInteractor,
        val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    val currentMode = MutableLiveData<Mode>().also {
        it.value = Mode.TEACHER
    }

    private val schoolsSubject by lazy {
        PublishSubject.create<Boolean>()
    }

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

    // Password Screen
    val password = MutableLiveData<String>()

    // Phone
    val phoneNo = MutableLiveData<PhoneModel>()

    // Profile Photo
    val profilePhoto = MutableLiveData<String>()

    // School logo
    val schoolLogo = MutableLiveData<String>()

    fun signUpUser(): Completable {
        return when (currentMode.value!!) {
            Mode.TEACHER -> signUpTeacher()
            Mode.SCHOOL -> signUpSchool()
        }
    }

    val schools = schoolsSubject
            .startWith(true)
            .flatMapSingle { getSchoolInteractor() }
            .replay()
            .autoConnect(1)

    fun loadSchools() {
        schoolsSubject.onNext(true)
    }

    private fun signUpSchool(): Completable {
        val schoolModel = SchoolModel(
                nameOfAuthority.value ?: "",
                schoolName.value ?: "",
                schoolEmail.value ?: "",
                schoolAreaCode.value ?: "",
                schoolCity.value ?: "",
                phoneNo.value ?: PhoneModel(),
                profilePhoto.value ?: "",
                schoolLogo.value ?: "",
                password.value ?: ""
        )
        return signUpSchoolInteractor(
                schoolModel
        )
    }

    private fun signUpTeacher(): Completable {
        val teacherModel = TeacherModel(
                firstName.value ?: "",
                lastName.value ?: "",
                school.value ?: "",
                teacher.value ?: false,
                classValue.value ?: "",
                sectionValue.value ?: "",
                phoneNo.value ?: PhoneModel(),
                profilePhoto.value ?: "",
                password.value ?: ""
        )
        return signUpTeacherInteractor(
                teacherModel
        )
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