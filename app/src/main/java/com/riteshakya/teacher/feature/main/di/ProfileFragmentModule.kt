package com.riteshakya.teacher.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.teacher.feature.main.ui.home.HomeFragment
import com.riteshakya.teacher.feature.main.ui.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ProfileFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesProfileFragment(): ProfileFragment
}