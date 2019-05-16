package com.riteshakya.teacher.feature.login.ui.signup.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
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
    }

    private fun navigateToPhone() {
        navigator.navigateToPhone(this)
    }
}