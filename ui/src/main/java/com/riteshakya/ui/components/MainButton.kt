package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.core.extension.getDimensionPixelSize
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_main_button.view.*


/**
 * author riteshakya037
 */
class MainButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var text: String = ""
        set(value) {
            field = value.split("\\s".toRegex()).joinToString("\n")
            textTxt.text = field
        }
    var src: Int = 0
        set(value) {
            field = value
            imageView.setImageResource(value)
        }

    init {
        init()
        initTypedArray(attrs)
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_main_button, this)
        isClickable = true
        isFocusable = true
        setBackgroundResource(R.drawable.background_main_button)
        val padding = context.getDimensionPixelSize(R.dimen.vertical_padding)
        setPadding(padding, padding, padding, padding)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.MainButton, 0, 0)
        text = ta.getString(R.styleable.MainButton_android_text) ?: ""
        src = ta.getResourceId(R.styleable.WelcomeView_android_src, 0)
        ta.recycle()
    }
}
