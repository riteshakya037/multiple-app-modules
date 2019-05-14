package com.riteshakya.core.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.platform.BaseFragment
import javax.inject.Inject

class Navigator @Inject constructor() {

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

    fun openUrl(context: Context, url: String) {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}