package com.riteshakya.teacher.feature.login.di

import androidx.lifecycle.ViewModel
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.teacher.feature.login.ui.signup.SignUpFragment
import com.riteshakya.teacher.feature.login.ui.signup.password.PasswordFragment
import com.riteshakya.teacher.feature.login.ui.signup.phone.PhoneFragment
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module(includes = [SignUpModule.ProvideViewModel::class])
abstract class SignUpModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesSignUpFragment(): SignUpFragment


    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesPasswordFragment(): PasswordFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesPhoneFragment(): PhoneFragment


    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SignUpViewModel::class)
        fun provideSignUpViewModel(
                failureMessageMapper: FailureMessageMapper
        ): ViewModel = SignUpViewModel(failureMessageMapper)
    }
}