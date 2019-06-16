package com.riteshakya.student.feature.login.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.businesslogic.repository.student.model.FEMALE
import com.riteshakya.businesslogic.repository.student.model.MALE
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.core.validation.types.NameValidation
import com.riteshakya.student.R
import com.riteshakya.student.feature.login.navigation.LoginNavigator
import com.riteshakya.student.feature.login.vm.SignUpViewModel
import com.riteshakya.ui.components.SpinnerAdapter
import kotlinx.android.synthetic.main.fragment_sign_up.*
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
        nextBtn.setOnClickListener {
            navigateToPassword()
        }
        initializeValidators()
        initializeSchools()
        initializeVm()
    }


    private fun initializeValidators() {
        addValidationList(firstNameTxt.addValidity(NameValidation(), {
            signUpViewModel.firstName.value = it
        }))
        addValidationList(lastNameTxt.addValidity(NameValidation(), {
            signUpViewModel.lastName.value = it
        }))
        addValidationList(schoolSelect.addValidity {
            signUpViewModel.school.value = it
        })
        genderSwitch.addOnTabSelectedListener {
            signUpViewModel.gender.value = if (it == 0) MALE else FEMALE
        }
        classSelect.onItemChanged {
            signUpViewModel.classValue.value = it
        }
        sectionSelect.onItemChanged {
            signUpViewModel.sectionValue.value = it
        }
    }

    private fun initializeVm() {
        firstNameTxt.setText(signUpViewModel.firstName.value)
        lastNameTxt.setText(signUpViewModel.lastName.value)
        schoolSelect.setSelection(signUpViewModel.school.value)
        classSelect.setSelection(signUpViewModel.classValue.value)
        sectionSelect.setSelection(signUpViewModel.sectionValue.value)
        genderSwitch.setScrollPosition(if (signUpViewModel.gender.value == MALE) 0 else 1)
    }

    private fun initializeSchools() {
        signUpViewModel.schools
            .addLoading()
            .subscribe {
                schoolSelect.items =
                    it.map { school ->
                        SpinnerAdapter.SpinnerModel(
                            school.id, school.schoolName, school.schoolLogo
                        )
                    }
            }
            .untilStop()
    }

    private fun navigateToPassword() {
        navigator.navigateToPassword(this)
    }

    override fun setValidity(result: Boolean) {
        nextBtn.isEnabled = result
    }
}