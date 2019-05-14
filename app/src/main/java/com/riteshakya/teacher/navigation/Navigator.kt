package com.riteshakya.teacher.navigation

import android.content.Context
import com.riteshakya.teacher.feature.login.ui.LoginActivity
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Navigator
@Inject constructor() {
    fun showMain(context: Context) {
        navigateToLogin(context)
    }

    private fun navigateToLogin(context: Context) = context.startActivity(LoginActivity.callingIntent(context))

}


