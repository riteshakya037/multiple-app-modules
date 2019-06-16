package com.riteshakya.student.feature.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.businesslogic.interactor.school.GetSchoolsInteractor
import com.riteshakya.businesslogic.interactor.signup.SignUpStudentInteractor
import com.riteshakya.businesslogic.repository.auth.PhoneRepository
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.student.feature.login.ui.signup.SignUpFragment
import com.riteshakya.student.feature.login.ui.signup.password.PasswordFragment
import com.riteshakya.student.feature.login.ui.signup.phone.PhoneFragment
import com.riteshakya.student.feature.login.ui.signup.profilepicture.ProfilePictureFragment
import com.riteshakya.student.feature.login.vm.PhoneVerificationViewModel
import com.riteshakya.student.feature.login.vm.SignUpViewModel
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
    @ContributesAndroidInjector(modules = [InjectPhoneVerificationViewModel::class])
    abstract fun providesPhoneFragment(): PhoneFragment

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesProfilePictureFragment(): ProfilePictureFragment


    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SignUpViewModel::class)
        fun provideSignUpViewModel(
            getSchoolsInteractor: GetSchoolsInteractor,
            signUpStudentInteractor: SignUpStudentInteractor
        ): ViewModel = SignUpViewModel(getSchoolsInteractor, signUpStudentInteractor)

        @Provides
        @IntoMap
        @ViewModelKey(PhoneVerificationViewModel::class)
        fun providePhoneVerificationViewModel(
            failureMessageMapper: FailureMessageMapper,
            phoneRepository: PhoneRepository
        ): ViewModel = PhoneVerificationViewModel(phoneRepository, failureMessageMapper)
    }

    @Module
    class InjectPhoneVerificationViewModel {

        @Provides
        fun provideLoginViewModel(
            factory: ViewModelProvider.Factory,
            target: PhoneFragment
        ): PhoneVerificationViewModel =
            ViewModelProviders.of(target, factory).get(PhoneVerificationViewModel::class.java)
    }
}