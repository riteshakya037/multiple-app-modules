package com.riteshakya.teacher.data.datasource.auth

import com.riteshakya.teacher.data.datasource.auth.repository.FirebaseAuthRepository
import com.riteshakya.teacher.repository.auth.AuthRepository
import dagger.Module
import dagger.Provides

class AuthModule {

    @Module
    class Repositories {
        @Provides
        fun provideAuthRepository(repository: FirebaseAuthRepository): AuthRepository = repository
    }
}

