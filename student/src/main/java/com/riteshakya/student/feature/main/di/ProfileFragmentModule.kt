package com.riteshakya.student.feature.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.businesslogic.interactor.login.LogoutUserInteractor
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.student.feature.main.ui.profile.ProfileFragment
import com.riteshakya.student.feature.main.vm.ProfileViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [ProfileFragmentModule.ProvideViewModel::class])
abstract class ProfileFragmentModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesProfileFragment(): ProfileFragment


    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(ProfileViewModel::class)
        fun provideProfileViewModel(
                logoutUserInteractor: LogoutUserInteractor
        ): ViewModel = ProfileViewModel(
                logoutUserInteractor
        )
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideProfileViewModel(
                factory: ViewModelProvider.Factory,
                target: ProfileFragment
        ): ProfileViewModel =
                ViewModelProviders.of(target, factory).get(ProfileViewModel::class.java)
    }
}