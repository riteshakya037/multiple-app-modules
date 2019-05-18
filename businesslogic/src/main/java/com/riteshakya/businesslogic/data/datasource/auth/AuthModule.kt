package com.riteshakya.businesslogic.data.datasource.auth

import com.riteshakya.businesslogic.data.datasource.auth.repository.FirebaseAuthRepository
import com.riteshakya.businesslogic.data.datasource.auth.repository.FirebasePhoneRepository
import com.riteshakya.businesslogic.repository.auth.AuthRepository
import com.riteshakya.businesslogic.repository.auth.PhoneRepository
import dagger.Module
import dagger.Provides

class AuthModule {

    @Module
    class Repositories {
        @Provides
        fun provideAuthRepository(repository: FirebaseAuthRepository): AuthRepository = repository

        @Provides
        fun providePhoneRepository(repository: FirebasePhoneRepository): PhoneRepository = repository
    }
}

