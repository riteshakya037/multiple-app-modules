package com.riteshakya.core.platform

import androidx.navigation.fragment.NavHostFragment
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity() {

    abstract val navHost: NavHostFragment

    override fun onBackPressed() {
        val baseFragment = navHost.childFragmentManager.primaryNavigationFragment
            ?: super.onBackPressed()
        if (baseFragment is BaseFragment) {
            if (!baseFragment.consumeBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }
}