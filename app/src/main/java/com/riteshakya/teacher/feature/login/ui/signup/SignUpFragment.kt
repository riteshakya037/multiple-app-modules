package com.riteshakya.teacher.feature.login.ui.signup

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.riteshakya.core.extension.addMovementMethod
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.core.validation.types.EmailValidation
import com.riteshakya.core.validation.types.LengthValidation
import com.riteshakya.core.validation.types.NameValidation
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import com.riteshakya.ui.helpers.CustomClickableSpan
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up_school.*
import kotlinx.android.synthetic.main.fragment_sign_up_teacher.*
import javax.inject.Inject

class SignUpFragment : BaseFragment() {
    @Inject
    internal lateinit var navigator: LoginNavigator

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addModeChangeListener()
        initializeFooter()
        nextBtn.setOnClickListener {
            navigateToPassword()
        }
        initializeMode()
        initializeValidators()
    }

    private fun addModeChangeListener() {
        modeSwitch.addOnTabSelectedListener {
            modeFlipper.displayedChild = (it)
            signUpViewModel.currentMode.value = SignUpViewModel.Mode.getMode(it)
        }
    }


    private fun addTeacherSwitchListener() {
        teacherSwitch.setOnCheckedChangeListener {
            signUpViewModel.teacher.value = it
            teacherSpinnerLayout.visibility = if (it) VISIBLE else GONE
        }
    }

    private fun initializeValidators() {
        signUpViewModel.currentMode.observe(this, Observer {
            resetValidationList()
            when (it) {
                SignUpViewModel.Mode.TEACHER -> initializeTeacherValidators()
                SignUpViewModel.Mode.SCHOOL -> initializeSchoolValidators()
                else -> {
                    return@Observer
                }
            }
            initializeInputsFromVM(it)
        })
    }

    private fun initializeFooter() {
        val footerText = SpannableStringBuilder()
        footerText.append(context!!.getString(R.string.txt_school_not_listed))
        footerText.append(
                context!!.getString(R.string.txt_add_here),
                object : CustomClickableSpan(context!!, R.color.colorBlue) {
                    override fun onClick(p0: View) {
                        modeSwitch.setScrollPosition(1)
                    }
                }, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        teacherFooterText.text = footerText
        teacherFooterText.addMovementMethod()
    }

    private fun initializeInputsFromVM(mode: SignUpViewModel.Mode) {
        when (mode) {
            SignUpViewModel.Mode.TEACHER -> initializeTeacherVm()
            SignUpViewModel.Mode.SCHOOL -> initializeSchoolVM()
        }
    }

    private fun initializeMode() {
        signUpViewModel.currentMode.value?.also { modeSwitch.setScrollPosition(it.mode) }
    }


    private fun initializeSchoolValidators() {
        addValidationList(nameOfAuthorityTxt.addValidity(NameValidation(), {
            signUpViewModel.nameOfAuthority.value = it
        }))
        addValidationList(schoolNameTxt.addValidity(NameValidation(), {
            signUpViewModel.schoolName.value = it
        }))
        addValidationList(schoolEmailTxt.addValidity(EmailValidation(), {
            signUpViewModel.schoolEmail.value = it
        }))
        addValidationList(schoolAreaPinTxt.addValidity(LengthValidation(5), {
            signUpViewModel.schoolAreaCode.value = it
        }))
        addValidationList(cityNameTxt.addValidity(NameValidation(), {
            signUpViewModel.schoolCity.value = it
        }))
    }

    private fun initializeSchoolVM() {
        nameOfAuthorityTxt.setText(signUpViewModel.nameOfAuthority.value)
        schoolNameTxt.setText(signUpViewModel.schoolName.value)
        schoolEmailTxt.setText(signUpViewModel.schoolEmail.value)
        schoolAreaPinTxt.setText(signUpViewModel.schoolAreaCode.value)
        cityNameTxt.setText(signUpViewModel.schoolCity.value)
    }

    private fun initializeTeacherValidators() {
        addValidationList(firstNameTxt.addValidity(NameValidation(), {
            signUpViewModel.firstName.value = it
        }))
        addValidationList(lastNameTxt.addValidity(NameValidation(), {
            signUpViewModel.lastName.value = it
        }))
        addValidationList(schoolSelect.addValidity {
            signUpViewModel.school.value = it
        })
        addTeacherSwitchListener()
        classSelect.onItemChanged {
            signUpViewModel.classValue.value = it
        }
        sectionSelect.onItemChanged {
            signUpViewModel.sectionValue.value = it
        }
    }

    private fun initializeTeacherVm() {
        firstNameTxt.setText(signUpViewModel.firstName.value)
        lastNameTxt.setText(signUpViewModel.lastName.value)
        schoolSelect.setSelection(signUpViewModel.school.value)
        classSelect.setSelection(signUpViewModel.classValue.value)
        sectionSelect.setSelection(signUpViewModel.sectionValue.value)
        teacherSwitch.isChecked = signUpViewModel.teacher.value ?: true
    }

    private fun navigateToPassword() {
        navigator.navigateToPassword(this)
    }

    override fun setValidity(result: Boolean) {
        nextBtn.isEnabled = result
    }
}