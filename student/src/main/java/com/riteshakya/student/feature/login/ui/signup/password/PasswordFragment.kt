package com.riteshakya.student.feature.login.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.core.validation.types.PasswordValidation
import com.riteshakya.student.R
import com.riteshakya.student.feature.login.navigation.LoginNavigator
import com.riteshakya.student.feature.login.vm.SignUpViewModel
import com.riteshakya.ui.helpers.EqualToOtherFieldValidation
import kotlinx.android.synthetic.main.fragment_password.*
import javax.inject.Inject

class PasswordFragment : BaseFragment() {

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var navigator: LoginNavigator


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_password, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nextBtn.setOnClickListener {
            navigateToPhone()
        }
        initializeValidators()
        passwordTxt.setText(signUpViewModel.password.value)
        confirmPasswordTxt.setText(signUpViewModel.password.value)
    }


    private fun initializeValidators() {
        addValidationList(passwordTxt.addValidity(PasswordValidation()))
        addValidationList(
                confirmPasswordTxt.addValidity(
                        EqualToOtherFieldValidation(passwordTxt),
                        { signUpViewModel.password.value = it }
                )
        )
    }

    override fun setValidity(result: Boolean) {
        nextBtn.isEnabled = result
    }


    private fun navigateToPhone() {
        navigator.navigateToPhone(this)
    }
}