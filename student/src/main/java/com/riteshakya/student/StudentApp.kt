package com.riteshakya.student

import com.riteshakya.student.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class StudentApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}