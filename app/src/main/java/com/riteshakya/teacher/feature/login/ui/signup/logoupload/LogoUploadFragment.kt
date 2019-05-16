package com.riteshakya.teacher.feature.login.ui.signup.logoupload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_logo_upload.*
import javax.inject.Inject

class LogoUploadFragment : BaseFragment() {

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var navigator: LoginNavigator

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_logo_upload, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        finishBtn.setOnClickListener {
            signUpViewModel.signUpUser()
        }

    }
}