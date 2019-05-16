package com.riteshakya.teacher.navigation

import android.content.Context
import androidx.fragment.app.Fragment
import com.riteshakya.core.navigation.NavigationHelper
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.ui.LoginActivity
import com.riteshakya.teacher.feature.main.ui.MainActivity
import com.riteshakya.ui.components.ViewPagerFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Navigator
@Inject constructor() {
    fun showMain(context: Context) {
        navigateToLogin(context)
    }

    private fun navigateToLogin(context: Context) = context.startActivity(
            LoginActivity.callingIntent(context)
    )

    private fun navigateToMain(context: Context) = context.startActivity(
            MainActivity.callingIntent(context)
    )

    fun addHolderFragment(fragmentId: Int): Fragment {
        return ViewPagerFragment.newInstance(
                R.navigation.nav_graph_main, fragmentId
        )
    }
}


