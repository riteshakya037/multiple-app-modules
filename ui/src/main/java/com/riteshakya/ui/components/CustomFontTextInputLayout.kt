package com.riteshakya.ui.components

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.textfield.TextInputLayout
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.TypefaceSpan


class CustomFontTextInputLayout(
        context: Context,
        attrs: AttributeSet? = null
) : TextInputLayout(context, attrs) {
    private val boldTypeFace by lazy {
        Typeface.create(
                ResourcesCompat.getFont(context, R.font.montserrat_bold),
                Typeface.NORMAL
        )
    }
    private val semiBoldTypeFace by lazy {
        Typeface.create(
                ResourcesCompat.getFont(context, R.font.montserrat_semibold),
                Typeface.NORMAL
        )
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        if (editText !is CustomFontTextInputEditText && !isInEditMode) {
            typeface = boldTypeFace
        }
    }

    fun shouldChangeFont(focused: Boolean) {
        if (!isInEditMode) {
            typeface = if (focused || editText?.text!!.isNotEmpty() || editText?.error != null) {
                boldTypeFace
            } else {
                semiBoldTypeFace
            }
        }
    }

    override fun setError(errorText: CharSequence?) {
        if (errorText != null && !isInEditMode) {
            val s = SpannableString(errorText)
            s.setSpan(
                    TypefaceSpan(semiBoldTypeFace), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            super.setError(s)
        } else {
            super.setError(errorText)
        }
    }

    override fun setHelperText(helperText: CharSequence?) {
        if (helperText != null && !isInEditMode) {
            val s = SpannableString(helperText)
            s.setSpan(
                    TypefaceSpan(semiBoldTypeFace), 0, s.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            super.setHelperText(s)
        } else {
            super.setHelperText(helperText)
        }
    }
}