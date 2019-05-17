package com.riteshakya.core.navigation

import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.platform.BaseFragment
import javax.inject.Inject

class NavigationController @Inject constructor() {

    fun navigateTo(
            fragment: BaseFragment,
            navigationHelper: NavigationHelper,
            navBuilder: NavOptions.Builder = defaultNavOptions()
    ) {
        NavHostFragment.findNavController(fragment).navigate(
                navigationHelper.navigationDirection,
                navBuilder.build()
        )
    }
}