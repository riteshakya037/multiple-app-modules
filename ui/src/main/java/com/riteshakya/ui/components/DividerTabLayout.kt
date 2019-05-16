package com.riteshakya.ui.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.google.android.material.tabs.TabLayout
import com.riteshakya.core.extension.toPixels
import com.riteshakya.ui.R


class DividerTabLayout(
        context: Context,
        attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

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

    fun setScrollPosition(position: Int) {
        getTabAt(position)?.select()
    }

    fun addOnTabSelectedListener(callbacks: (Int) -> Unit) {
        addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabReselected(p0: Tab?) {

            }

            override fun onTabUnselected(p0: Tab?) {

            }

            override fun onTabSelected(tab: Tab) {
                callbacks(tab.position)
            }
        })
    }
}