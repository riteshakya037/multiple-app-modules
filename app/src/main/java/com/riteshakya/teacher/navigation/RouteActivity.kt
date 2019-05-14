package com.riteshakya.teacher.navigation

import android.os.Bundle
import dagger.android.DaggerActivity
import javax.inject.Inject

class RouteActivity : DaggerActivity() {

    @Inject
    internal lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.showMain(this)
    }
}
