package com.riteshakya.ui.components

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.core.extension.getDimensionPixelSize
import com.riteshakya.core.extension.setMaxLength
import com.riteshakya.core.extension.setSelectivePadding
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.Status
import kotlinx.android.synthetic.main.custom_text_input.view.*


class TextInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var passwordEnabled: Boolean = false
        set(value) {
            inputTxtLayout.isPasswordVisibilityToggleEnabled = value
            if (value) {
                inputTxt.transformationMethod = PasswordTransformationMethod()
            } else {
                inputTxt.transformationMethod = null
            }
            field = value
        }

    var inputType: Int = InputType.TYPE_NULL
        set(value) {
            inputTxt.inputType = value
            field = value
        }

    var imeOptions: Int = InputType.TYPE_NULL
        set(value) {
            inputTxt.imeOptions = value
            field = value
        }

    var hint: String = ""
        set(value) {
            inputTxtLayout.hint = value
            field = value
        }

    var status: Status = Status.NONE
        set(state) {
            inputStatus.displayedChild = state.value
            val paddingRight = if (state == Status.NONE) 0 else context.getDimensionPixelSize(
                    R.dimen.input_icon_padding
            )
            inputTxt.setSelectivePadding(right = paddingRight)
            field = state
        }

    var maxLength: Int = Integer.MAX_VALUE
        set(value) {
            inputTxt.setMaxLength(maxLength)
            field = value
        }

    init {
        init()
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.TextInput, 0, 0)
        hint = ta.getString(R.styleable.TextInput_android_hint) ?: ""
        inputType = ta.getInt(R.styleable.TextInput_android_inputType, InputType.TYPE_NULL)
        passwordEnabled = ta.getBoolean(R.styleable.TextInput_password, false)
        imeOptions = ta.getInt(R.styleable.TextInput_android_imeOptions, EditorInfo.IME_NULL)
        status = Status.fromId(ta.getInt(R.styleable.TextInput_status, Status.NONE.value))
        maxLength = ta.getInt(R.styleable.TextInput_android_maxLength, Integer.MAX_VALUE)
        ta.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_text_input, this)
        inputTxt.imeOptions
    }

}