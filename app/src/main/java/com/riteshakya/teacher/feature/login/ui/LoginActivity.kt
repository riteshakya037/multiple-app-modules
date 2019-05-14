package com.riteshakya.teacher.feature.login.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.platform.BaseActivity
import com.riteshakya.teacher.R
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, LoginActivity::class.java)
    }

    override val navHost: NavHostFragment by lazy { nav_host_fragment as NavHostFragment }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
