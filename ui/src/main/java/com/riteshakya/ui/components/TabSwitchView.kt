package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.tabs.TabLayout


open class TabSwitchView(
        context: Context,
        attrs: AttributeSet? = null
) : TabLayout(context, attrs) {

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