package com.riteshakya.teacher.feature.main.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.platform.BaseActivity
import com.riteshakya.teacher.R
import com.riteshakya.teacher.navigation.Navigator
import com.riteshakya.ui.adapters.MainPageFragmentAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    @Inject
    lateinit var mainPagerAdapter: MainPageFragmentAdapter

    @Inject
    internal lateinit var navigator: Navigator

    override val navHost: NavHostFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager.adapter = mainPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        addNecessaryTabs()

        mainPagerAdapter.registerTabLayout(tabLayout)
    }

    private fun addNecessaryTabs() {
        addTab(navigator.addHolderFragment(R.id.homeFragment), R.drawable.ic_home)
        addTab(navigator.addHolderFragment(R.id.newsFragment), R.drawable.ic_news)
        addTab(navigator.addHolderFragment(R.id.notificationFragment), R.drawable.ic_notification)
        addTab(navigator.addHolderFragment(R.id.profileFragment), R.drawable.ic_profile)
    }

    private fun addTab(fragment: Fragment, icon: Int) {
        mainPagerAdapter.addFragment(fragment, icon)
    }


}
