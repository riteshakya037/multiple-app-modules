package com.riteshakya.teacher.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.ui.components.ViewPagerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ViewPagerModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesViewPagerFragment(): ViewPagerFragment
}