package com.riteshakya.teacher.feature.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.teacher.feature.main.ui.home.HomeFragment
import com.riteshakya.teacher.feature.main.vm.HomeViewModel
import com.riteshakya.businesslogic.interactor.user.GetCurrentUserInteractor
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [HomeFragmentModule.ProvideViewModel::class])
abstract class HomeFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesHomeFragment(): HomeFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(HomeViewModel::class)
        fun provideHomeViewModel(
                getCurrentUserInteractor: GetCurrentUserInteractor
        ): ViewModel = HomeViewModel(
                getCurrentUserInteractor
        )
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideHomeViewModel(
                factory: ViewModelProvider.Factory,
                target: HomeFragment
        ): HomeViewModel = ViewModelProviders.of(target, factory).get(HomeViewModel::class.java)
    }
}