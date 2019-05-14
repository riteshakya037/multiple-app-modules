package com.riteshakya.teacher.feature.login.di

import com.riteshakya.core.di.PerActivity
import com.riteshakya.teacher.feature.login.ui.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @PerActivity
    @ContributesAndroidInjector(modules = [FragmentModules::class])
    abstract fun provideLoginActivityFactory(): LoginActivity

    @Module(includes = [OnBoardingModule::class, LoginModule::class])
    class FragmentModules
}