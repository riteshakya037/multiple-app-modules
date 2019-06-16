package com.riteshakya.student.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.businesslogic.interactor.school.GetSchoolsInteractor
import com.riteshakya.businesslogic.interactor.signup.SignUpStudentInteractor
import com.riteshakya.businesslogic.repository.student.model.Gender
import com.riteshakya.businesslogic.repository.student.model.MALE
import com.riteshakya.businesslogic.repository.student.model.StudentModel
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import io.reactivex.Completable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class SignUpViewModel
@Inject constructor(
    val getSchoolInteractor: GetSchoolsInteractor,
    val signUpStudentInteractor: SignUpStudentInteractor
) : BaseViewModel() {
    private val schoolsSubject by lazy {
        PublishSubject.create<Boolean>()
    }

    // SignUp student screen
    val firstName = MutableLiveData<String>()
    val lastName = MutableLiveData<String>()
    val school = MutableLiveData<String>()
    val gender = MutableLiveData<@Gender String>().also {
        it.value = MALE
    }
    val classValue = MutableLiveData<String>()
    val sectionValue = MutableLiveData<String>()

    // Password Screen
    val password = MutableLiveData<String>()

    // Phone
    val phoneNo = MutableLiveData<PhoneModel>()

    // Profile Photo
    val profilePhoto = MutableLiveData<String>()

    val schools = schoolsSubject
        .startWith(true)
        .flatMapSingle { getSchoolInteractor() }
        .replay()
        .autoConnect(1)


    fun signUpUser(): Completable {
        val student = StudentModel(
            firstName.value ?: "",
            lastName.value ?: "",
            school.value ?: "",
            gender.value ?: MALE,
            classValue.value ?: "",
            sectionValue.value ?: "",
            phoneNo.value ?: PhoneModel(),
            profilePhoto.value ?: "",
            password.value ?: ""
        )
        return signUpStudentInteractor(student)
    }
}