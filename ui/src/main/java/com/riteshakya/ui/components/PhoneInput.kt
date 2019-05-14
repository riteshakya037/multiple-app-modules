package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.core.extension.getDimensionPixelSize
import com.riteshakya.core.extension.getViewWidth
import com.riteshakya.core.extension.onTextChanged
import com.riteshakya.core.extension.setSelectivePadding
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.Status
import kotlinx.android.synthetic.main.custom_phone_input.view.*

class PhoneInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var inputChangeListener: ((String, String) -> Unit)? = null
    var hint: String = ""
        set(value) {
            hintTxt.text = value
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

    init {
        init()
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.PhoneInput, 0, 0)
        hint = ta.getString(R.styleable.PhoneInput_android_hint) ?: ""
        status = Status.fromId(
                ta.getInt(R.styleable.PhoneInput_status, Status.NONE.value)
        )
        ta.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_phone_input, this)
        countrySpinner.onCodeChange {
            inputChangeListener?.invoke(it, inputTxt.text.toString())
        }
        inputTxt.onTextChanged {
            inputChangeListener?.invoke(countrySpinner.dialCode, inputTxt.text.toString())
        }
    }

    fun onInputChanged(inputChangeListener: (dialCode: String, phoneNo: String) -> Unit) {
        this.inputChangeListener = inputChangeListener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        countrySpinner.dropDownWidth = getViewWidth()
    }

}