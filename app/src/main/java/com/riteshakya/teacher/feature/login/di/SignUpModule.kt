package com.riteshakya.teacher.feature.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.teacher.feature.login.ui.signup.SignUpFragment
import com.riteshakya.teacher.feature.login.vm.LoginViewModel
import com.riteshakya.teacher.feature.login.vm.SignupViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [SignUpModule.ProvideViewModel::class])
abstract class SignUpModule {


    @PerFragment
    @ContributesAndroidInjector(modules = [InjectViewModel::class])
    abstract fun providesSignUpFragment(): SignUpFragment

    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SignupViewModel::class)
        fun provideSignUpViewModel(
                failureMessageMapper: FailureMessageMapper
        ): ViewModel = SignupViewModel(failureMessageMapper)
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideSignUpViewModel(
                factory: ViewModelProvider.Factory,
                target: SignUpFragment
        ): SignupViewModel =
                ViewModelProviders.of(target, factory).get(SignupViewModel::class.java)
    }

}