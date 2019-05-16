package com.riteshakya.teacher.feature.login.ui.signup.profilepicture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_profile_picture.*
import javax.inject.Inject

class ProfilePictureFragment : BaseFragment() {

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel

    @Inject
    lateinit var navigator: LoginNavigator

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile_picture, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel.currentMode.observe(this, Observer {
            it?.apply {
                when (this) {
                    SignUpViewModel.Mode.TEACHER -> {
                        finishBtn.text = "Finish"
                        finishBtn.setOnClickListener {
                            signUpViewModel.signUpUser()
                        }
                    }
                    SignUpViewModel.Mode.SCHOOL -> {
                        finishBtn.text = "Next"
                        finishBtn.setOnClickListener {
                            navigateToLogoUpload()
                        }
                    }
                }
            }
        })

    }

    private fun navigateToLogoUpload() {
        navigator.navigateToLogoUpload(this@ProfilePictureFragment)
    }
}