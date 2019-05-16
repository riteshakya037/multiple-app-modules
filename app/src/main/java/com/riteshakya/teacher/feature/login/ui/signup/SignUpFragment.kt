package com.riteshakya.teacher.feature.login.ui.signup

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.riteshakya.core.extension.addMovementMethod
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import com.riteshakya.ui.helpers.CustomClickableSpan
import kotlinx.android.synthetic.main.fragment_sign_up.*
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
        addTeacherSwitchListener()
        initializeFooter()
        nextBtn.setOnClickListener {
            navigateToPassword()
        }
        initializeMode()
    }

    private fun addTeacherSwitchListener() {
        teacherSwitch.setOnCheckedChangeListener {
            teacherSpinnerLayout.visibility = if (it) VISIBLE else GONE
        }
    }

    private fun addModeChangeListener() {
        modeSwitch.addOnTabSelectedListener {
            modeFlipper.displayedChild = (it)
            signUpViewModel.currentMode.value = SignUpViewModel.Mode.getMode(it)
        }
    }

    private fun navigateToPassword() {
        navigator.navigateToPassword(this)
    }

    private fun initializeMode() {
        signUpViewModel.currentMode.value?.also { modeSwitch.setScrollPosition(it.mode) }
    }

    private fun initializeFooter() {
        val footerText = SpannableStringBuilder()
        footerText.append("school not listed? ")
        footerText.append(
                "add here",
                object : CustomClickableSpan(context!!, R.color.colorBlue) {
                    override fun onClick(p0: View) {
                        modeSwitch.setScrollPosition(1)
                    }
                }, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        teacherFooterText.text = footerText
        teacherFooterText.addMovementMethod()
    }
}