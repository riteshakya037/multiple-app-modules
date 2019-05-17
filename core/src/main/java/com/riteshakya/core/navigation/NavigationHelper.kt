package com.riteshakya.core.navigation

import android.os.Bundle
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import com.riteshakya.core.R

class NavigationHelper(
        val navigationDirection: NavDirections,
        val args: Bundle = Bundle(),
        val navBuilder: NavOptions.Builder = defaultNavOptions()
)

fun defaultNavOptions(): NavOptions.Builder {
    val navOptionsBuilder = NavOptions.Builder()
    navOptionsBuilder.setLaunchSingleTop(true)
    navOptionsBuilder.setEnterAnim(R.anim.slide_in_right)
    navOptionsBuilder.setExitAnim(R.anim.slide_out_left)
    navOptionsBuilder.setPopEnterAnim(R.anim.slide_in_left)
    navOptionsBuilder.setPopExitAnim(R.anim.slide_out_right)
    return navOptionsBuilder
}