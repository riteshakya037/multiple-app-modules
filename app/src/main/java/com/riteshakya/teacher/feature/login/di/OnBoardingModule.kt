package com.riteshakya.teacher.feature.login.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.teacher.feature.login.ui.onboarding.OnBoardingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class OnBoardingModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesLoginModuleFragment(): OnBoardingFragment

}