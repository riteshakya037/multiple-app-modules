package com.riteshakya.ui.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.animateChange
import kotlinx.android.synthetic.main.custom_image_tab.view.*
import javax.inject.Inject


class MainPageFragmentAdapter @Inject constructor(
        private val context: Context, fm: FragmentManager
) : FragmentPagerAdapter(fm) {
    private val models: ArrayList<Int> = ArrayList()
    private val pages: ArrayList<Fragment> = ArrayList()

    override fun getCount(): Int {
        return pages.size
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    fun addFragment(fragment: Fragment, @DrawableRes iconRes: Int) {
        pages.add(fragment)
        models.add(iconRes)
        notifyDataSetChanged()
    }

    private fun getTabView(index: Int): View {
        val v = LayoutInflater.from(context).inflate(R.layout.custom_image_tab, null, false)
        val color = if (index == 0) R.color.colorBlack else R.color.colorTextLight
        v.tab_image.imageTintList = ColorStateList.valueOf(
                ContextCompat.getColor(context, color)
        )
        v.tab_image.setImageResource(models[index])
        return v
    }

    private fun unSelectView(view: View?) {
        animateChange(
                ContextCompat.getColor(context, R.color.colorBlack),
                ContextCompat.getColor(context, R.color.colorTextLight)
        ) {
            view?.tab_image?.imageTintList = ColorStateList.valueOf(it)
        }
    }

    private fun selectView(view: View?) {
        animateChange(
                ContextCompat.getColor(context, R.color.colorTextLight),
                ContextCompat.getColor(context, R.color.colorBlack)
        ) {
            view?.tab_image?.imageTintList = ColorStateList.valueOf(it)
        }
    }

    fun registerTabLayout(tabLayout: TabLayout) {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                unSelectView(p0?.customView)
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                selectView(p0?.customView)
            }

            override fun onTabReselected(p0: TabLayout.Tab?) {
            }
        })

        models.forEachIndexed { index, _ ->
            val tabView = getTabView(index)
            val tabAt = tabLayout.getTabAt(index)!!
            tabAt.customView = tabView
        }
    }
}
