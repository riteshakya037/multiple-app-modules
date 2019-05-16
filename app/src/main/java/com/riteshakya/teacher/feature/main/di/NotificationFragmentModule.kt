package com.riteshakya.teacher.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.teacher.feature.main.ui.home.HomeFragment
import com.riteshakya.teacher.feature.main.ui.notification.NotificationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NotificationFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesNotificationFragment(): NotificationFragment
}