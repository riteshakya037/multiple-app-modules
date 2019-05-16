package com.riteshakya.teacher.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.teacher.feature.main.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesHomeFragment(): HomeFragment
}