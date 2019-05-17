package com.riteshakya.teacher.feature.login.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.riteshakya.core.di.PerFragment
import com.riteshakya.core.di.ViewModelKey
import com.riteshakya.core.exception.FailureMessageMapper
import com.riteshakya.teacher.feature.login.ui.signup.SignUpFragment
import com.riteshakya.teacher.feature.login.ui.signup.logoupload.LogoUploadFragment
import com.riteshakya.teacher.feature.login.ui.signup.password.PasswordFragment
import com.riteshakya.teacher.feature.login.ui.signup.phone.PhoneFragment
import com.riteshakya.teacher.feature.login.ui.signup.profilepicture.ProfilePictureFragment
import com.riteshakya.teacher.feature.login.vm.PhoneVerificationViewModel
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import com.riteshakya.teacher.interactor.school.GetSchoolsInteractor
import com.riteshakya.teacher.interactor.signup.SignUpSchoolInteractor
import com.riteshakya.teacher.interactor.signup.SignUpTeacherInteractor
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

    @PerFragment
    @ContributesAndroidInjector
    abstract fun providesLogoUploadFragment(): LogoUploadFragment


    @Module
    class ProvideViewModel {

        @Provides
        @IntoMap
        @ViewModelKey(SignUpViewModel::class)
        fun provideSignUpViewModel(
            signUpSchoolInteractor: SignUpSchoolInteractor,
            signUpTeacherInteractor: SignUpTeacherInteractor,
            getSchoolsInteractor: GetSchoolsInteractor,
            failureMessageMapper: FailureMessageMapper
        ): ViewModel = SignUpViewModel(
            signUpSchoolInteractor,
            signUpTeacherInteractor,
            getSchoolsInteractor,
            failureMessageMapper
        )

        @Provides
        @IntoMap
        @ViewModelKey(PhoneVerificationViewModel::class)
        fun providePhoneVerificationViewModel(
                failureMessageMapper: FailureMessageMapper
        ): ViewModel = PhoneVerificationViewModel(failureMessageMapper)
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