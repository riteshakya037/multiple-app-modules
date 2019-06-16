package com.riteshakya.teacher.feature.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.businesslogic.interactor.login.LoginUserInteractor
import com.riteshakya.businesslogic.interactor.login.LogoutUserInteractor
import com.riteshakya.businesslogic.interactor.school.GetSchoolsInteractor
import com.riteshakya.businesslogic.interactor.user.GetCurrentUserInteractor
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
        fun provideLoginViewModel(
                loginUserInteractor: LoginUserInteractor,
                getSchoolsInteractor: GetSchoolsInteractor,
                getCurrentUserInteractor: GetCurrentUserInteractor,
                logoutUserInteractor: LogoutUserInteractor,
                failureMessageMapper: FailureMessageMapper
        ): ViewModel = LoginViewModel(
                loginUserInteractor,
                getSchoolsInteractor,
                getCurrentUserInteractor,
                logoutUserInteractor,
                failureMessageMapper
        )
    }

    @Module
    class InjectViewModel {

        @Provides
        fun provideLoginViewModel(
                factory: ViewModelProvider.Factory,
                target: LoginFragment
        ): LoginViewModel =
                ViewModelProviders.of(target, factory).get(LoginViewModel::class.java)
    }
}