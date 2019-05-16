package com.riteshakya.ui.helpers

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import androidx.core.content.ContextCompat

abstract class CustomClickableSpan(val context: Context, val color: Int = -1) : ClickableSpan() {
    override fun updateDrawState(paint: TextPaint) {
        if (color != -1)
            paint.color = ContextCompat.getColor(context, color)
        paint.isUnderlineText = true
    }
}
