package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_select_input.view.*

class SelectInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var inputChangeListener: ((String) -> Unit)? = null
    var hint: String = ""
        set(value) {
            hintTxt.text = value
            field = value
        }

    init {
        init()
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.SelectInput, 0, 0)
        hint = ta.getString(R.styleable.SelectInput_android_hint) ?: ""
        ta.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_select_input, this)
        spinner.onItemChanged {
            inputChangeListener?.invoke(it)
        }
    }

    fun setItems(items: List<SpinnerAdapter.SpinnerModel>) {
        spinner.setItems(items)
    }

    fun onInputChanged(inputChangeListener: (value: String) -> Unit) {
        this.inputChangeListener = inputChangeListener
    }

}