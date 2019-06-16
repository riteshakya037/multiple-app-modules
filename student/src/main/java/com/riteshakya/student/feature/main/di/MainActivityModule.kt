package com.riteshakya.student.feature.main.di

import androidx.fragment.app.FragmentManager
import com.riteshakya.core.di.PerActivity
import com.riteshakya.student.feature.main.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module(includes = [])
abstract class MainActivityModule {
    @PerActivity
    @ContributesAndroidInjector(
            modules = [FragmentModules::class]
    )
    abstract fun provideMainActivityFactory(): MainActivity

    @Module(
            includes = [
                FragmentManagerProvider::class,
                ViewPagerModule::class,
                HomeFragmentModule::class,
                NewsFragmentModule::class,
                NotificationFragmentModule::class,
                ProfileFragmentModule::class
            ]
    )
    class FragmentModules


    @Module
    class FragmentManagerProvider {
        @Provides
        @PerActivity
        fun providesFragmentManager(
                activity: MainActivity
        ): FragmentManager = activity.supportFragmentManager
    }
}