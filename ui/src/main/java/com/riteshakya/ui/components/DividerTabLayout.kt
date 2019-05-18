package com.riteshakya.ui.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.riteshakya.core.extension.toPixels
import com.riteshakya.ui.R

class DividerTabLayout(
        context: Context,
        attrs: AttributeSet? = null
) : TabSwitchView(context, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val root = getChildAt(0)
        if (root is LinearLayout) {
            root.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
            val drawable = GradientDrawable()
            drawable.setColor(ContextCompat.getColor(context, R.color.colorBlack))
            drawable.setSize(context.toPixels(2), 1)
            root.dividerPadding = context.toPixels(2)
            root.dividerDrawable = drawable
        }
    }
}