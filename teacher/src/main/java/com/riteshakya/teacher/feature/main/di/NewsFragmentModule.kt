package com.riteshakya.teacher.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.teacher.feature.main.ui.news.NewsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesNewsFragment(): NewsFragment
}