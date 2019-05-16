package com.riteshakya.teacher.feature.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.core.validation.types.PasswordValidation
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment() {

    @Inject
    lateinit var loginViewModel: LoginViewModel
    @Inject
    lateinit var navigator: LoginNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_login, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeValidators()
        initializeInputsFromVM()
        loginBtn.setOnClickListener {
            loginViewModel.loginUser()
        }
    }

    private fun initializeInputsFromVM() {
        phoneSelector.setValue(loginViewModel.phoneNo.value)
        passwordTxt.setText(loginViewModel.password.value)
        schoolSelect.setSelection(loginViewModel.school.value)
    }

    private fun initializeValidators() {
        addValidationList(phoneSelector.addValidity {
            loginViewModel.phoneNo.value = it
        })
        addValidationList(passwordTxt.addValidity(PasswordValidation(), {
            loginViewModel.password.value = it
        }))
        addValidationList(schoolSelect.addValidity {
            loginViewModel.school.value = it
        })
    }

    override fun setValidity(result: Boolean) {
        loginBtn.isEnabled = result
    }
}
