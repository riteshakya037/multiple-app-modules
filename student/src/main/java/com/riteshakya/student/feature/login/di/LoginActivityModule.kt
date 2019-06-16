package com.riteshakya.student.feature.login.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.core.di.PerActivity
import com.riteshakya.student.feature.login.ui.LoginActivity
import com.riteshakya.student.feature.login.vm.SignUpViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class LoginActivityModule {
    @PerActivity
    @ContributesAndroidInjector(
            modules = [FragmentModules::class, CommonViewModels::class]
    )
    abstract fun provideLoginActivityFactory(): LoginActivity

    @Module(includes = [OnBoardingModule::class, LoginModule::class, SignUpModule::class])
    class FragmentModules

    @Module
    class CommonViewModels {

        @Provides
        fun provideSignUpViewModel(
                factory: ViewModelProvider.Factory,
                target: LoginActivity
        ): SignUpViewModel =
                ViewModelProviders.of(target, factory).get(SignUpViewModel::class.java)
    }
}