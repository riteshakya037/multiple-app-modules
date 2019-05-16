package com.riteshakya.teacher.feature.login.navigation

import androidx.navigation.ActionOnlyNavDirections
import com.riteshakya.core.navigation.NavigationController
import com.riteshakya.core.navigation.NavigationHelper
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginNavigator
@Inject constructor(
    private val navigationController: NavigationController
) {
    fun navigateToLogin(baseFragment: BaseFragment) {
        navigationController.navigateTo(baseFragment, NavigationHelper(ActionOnlyNavDirections(R.id.loginFragment)))
    }

    fun navigateToSignUp(fragment: BaseFragment) {
        navigationController.navigateTo(
                fragment, NavigationHelper(ActionOnlyNavDirections(R.id.signUpFragment))
        )
    }

}