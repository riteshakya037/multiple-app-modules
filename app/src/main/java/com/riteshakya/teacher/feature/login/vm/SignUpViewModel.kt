package com.riteshakya.teacher.feature.login.vm

import androidx.lifecycle.MutableLiveData
import com.riteshakya.businesslogic.interactor.school.GetSchoolsInteractor
import com.riteshakya.businesslogic.interactor.signup.SignUpSchoolInteractor
import com.riteshakya.businesslogic.interactor.signup.SignUpTeacherInteractor
import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import com.riteshakya.businesslogic.repository.teacher.model.TeacherModel
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.core.model.PhoneModel
import com.riteshakya.core.platform.BaseViewModel
import com.riteshakya.core.platform.ResultState
import com.riteshakya.teacher.interactor.geocode.GetCityNameInteractor
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject


class SignUpViewModel
@Inject constructor(
    val signUpSchoolInteractor: SignUpSchoolInteractor,
    val signUpTeacherInteractor: SignUpTeacherInteractor,
    val getSchoolInteractor: GetSchoolsInteractor,
    val getCityNameInteractor: GetCityNameInteractor,
    val failureMessageMapper: FailureMessageMapper
) : BaseViewModel() {
    val currentMode = MutableLiveData<Mode>().also {
        it.value = Mode.TEACHER
    }

    private val schoolsSubject by lazy {
        PublishSubject.create<Boolean>()
    }

    private val postalCodeSubject by lazy {
        PublishSubject.create<String>()
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

    val cityNameState: BehaviorSubject<ResultState> = BehaviorSubject.create()

    val schools = schoolsSubject
        .startWith(true)
        .flatMapSingle { getSchoolInteractor() }
        .replay()
        .autoConnect(1)

    val cityNameObserver = postalCodeSubject
        .filter { it.length == 5 }
        .flatMapSingle {
            getCityNameInteractor(it)
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    cityNameState.onNext(ResultState.Error(failureMessageMapper(it)))
                    ""
                }
        }
        .replay()
        .autoConnect(1)

    fun loadSchools() {
        schoolsSubject.onNext(true)
    }

    fun getCityFromPostalCode(postalCode: String) {
        postalCodeSubject.onNext(postalCode)
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