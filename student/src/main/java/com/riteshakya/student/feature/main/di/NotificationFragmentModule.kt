package com.riteshakya.student.feature.main.di

import com.riteshakya.core.di.PerFragment
import com.riteshakya.student.feature.main.ui.notification.NotificationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NotificationFragmentModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesNotificationFragment(): NotificationFragment
}