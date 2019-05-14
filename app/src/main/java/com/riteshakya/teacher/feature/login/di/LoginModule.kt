package com.riteshakya.teacher.feature.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.teacher.feature.login.ui.login.LoginFragment
import com.riteshakya.teacher.feature.login.vm.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [LoginModule.ProvideViewModel::class])
abstract class LoginModule {

    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesLoginModuleFragment(): LoginFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(LoginViewModel::class)
        fun provideAsteroidListViewModel(
            failureMessageMapper: FailureMessageMapper
        ): ViewModel = LoginViewModel(failureMessageMapper)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideAsteroidListViewModel(
            factory: ViewModelProvider.Factory,
            target: LoginFragment
        ): LoginViewModel =
            ViewModelProviders.of(target, factory).get(LoginViewModel::class.java)
    }
}