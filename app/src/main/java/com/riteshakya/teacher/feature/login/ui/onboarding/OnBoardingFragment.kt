package com.riteshakya.teacher.feature.login.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import kotlinx.android.synthetic.main.fragment_on_boarding.*
import javax.inject.Inject

class OnBoardingFragment : BaseFragment() {
    @Inject
    internal lateinit var navigator: LoginNavigator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_on_boarding, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn.setOnClickListener {
            navigateToLogin()
        }
        signUpBtn.setOnClickListener {
            navigateToSignUp()
        }
    }

    private fun navigateToSignUp() {
        navigator.navigateToSignUp(this)
    }

    private fun navigateToLogin() {
        navigator.navigateToLogin(this)
    }

}
