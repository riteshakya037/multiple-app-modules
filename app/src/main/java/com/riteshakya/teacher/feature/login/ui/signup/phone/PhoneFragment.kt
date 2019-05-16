package com.riteshakya.teacher.feature.login.ui.signup.phone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.PhoneVerificationViewModel
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_phone.*
import javax.inject.Inject

class PhoneFragment : BaseFragment() {

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel

    @Inject
    internal lateinit var phoneViewModel: PhoneVerificationViewModel

    @Inject
    lateinit var navigator: LoginNavigator

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_phone, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeButtonClickListeners()
        initializeValidators()
        initializeModeChanges()
        initializeInputsFromVM()
    }

    private fun initializeButtonClickListeners() {
        nextBtn.setOnClickListener {
            navigateToProfilePicture()
        }

        getCodeBtn.setOnClickListener {
            phoneViewModel.requestOrSubmitCode()
        }
        resendBtn.setOnClickListener {
            phoneViewModel.resendCode()
        }
    }

    private fun initializeModeChanges() {
        phoneViewModel.currentMode.observe(this, Observer {
            when (it) {
                PhoneVerificationViewModel.Mode.PHONE -> {
                    getCodeBtn.isEnabled = true
                    getCodeBtn.text = getString(R.string.txt_send_code)
                    waitingGroup.visibility = GONE
                }
                PhoneVerificationViewModel.Mode.WAITING_CODE -> {
                    verificationCodeView.performClick()
                    getCodeBtn.isEnabled = false
                    getCodeBtn.text = getString(R.string.txt_verify_code)
                    waitingGroup.visibility = VISIBLE
                }
                PhoneVerificationViewModel.Mode.VERIFIED -> {
                    getCodeBtn.isEnabled = true
                    getCodeBtn.text = getString(R.string.txt_send_code)
                    waitingGroup.visibility = GONE
                }
                else -> {
                    getCodeBtn.isEnabled = false
                    waitingGroup.visibility = GONE
                }
            }
        })
    }

    private fun initializeValidators() {
        addValidationList(phoneSelector.addValidity {
            phoneViewModel.phoneNo.value = it
            signUpViewModel.phoneNo.value = it
        }.doOnNext {
            phoneViewModel.enableRequestCode(it)
        })
        addValidationList(verificationCodeView.addValidity {
            phoneViewModel.code.value = it
        }.doOnNext {
            getCodeBtn.isEnabled = it ?: false
        })
        addValidationList(phoneViewModel.verifiedObservable)
    }

    private fun initializeInputsFromVM() {
        phoneSelector.setValue(signUpViewModel.phoneNo.value)
    }

    override fun setValidity(result: Boolean) {
        nextBtn.isEnabled = result
    }

    private fun navigateToProfilePicture() {
        navigator.navigateToProfilePicture(this)
    }
}