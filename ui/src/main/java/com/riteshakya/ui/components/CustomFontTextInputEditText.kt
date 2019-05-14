package com.riteshakya.ui.components

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText


class CustomFontTextInputEditText(
    context: Context,
    attrs: AttributeSet? = null
) : TextInputEditText(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (parent?.parent is CustomFontTextInputLayout) {
            (parent.parent as CustomFontTextInputLayout).shouldChangeFont(false)
        }
    }


    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (parent?.parent is CustomFontTextInputLayout) {
            (parent.parent as CustomFontTextInputLayout).shouldChangeFont(focused)
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        if (parent?.parent is CustomFontTextInputLayout) {
            (parent.parent as CustomFontTextInputLayout).shouldChangeFont(true)
        }
    }
}