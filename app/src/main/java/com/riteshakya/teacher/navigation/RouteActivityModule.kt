package com.riteshakya.teacher.navigation

import com.riteshakya.core.di.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RouteActivityModule {
    @PerActivity
    @ContributesAndroidInjector
    abstract fun provideRouteActivityFactory(): RouteActivity
}