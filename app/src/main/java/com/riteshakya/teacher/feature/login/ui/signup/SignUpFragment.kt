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
import com.riteshakya.ui.helpers.CustomClickableSpan
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up_teacher.*

class SignUpFragment : BaseFragment() {
    private var currentMode: Mode = Mode.TEACHER

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sign_up, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        modeSwitch.addOnTabSelectedListener {
            modeFlipper.displayedChild = (it)
            currentMode = Mode.getMode(it)
        }
        teacherSwitch.setOnCheckedChangeListener {
            teacherSpinnerLayout.visibility = if (it) VISIBLE else GONE
        }

        initializeFooter()
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


    enum class Mode(val mode: Int) {
        TEACHER(0), SCHOOL(1);

        companion object {
            @JvmStatic
            fun getMode(position: Int): Mode {
                for (f in values()) {
                    if (f.mode == position) return f
                }
                return TEACHER
            }
        }
    }
}