package com.riteshakya.businesslogic.data.datasource.user

import com.riteshakya.businesslogic.data.datasource.user.repository.FirestoreUserRepository
import com.riteshakya.businesslogic.repository.user.UserRepository
import dagger.Module
import dagger.Provides

class UserModule {

    @Module
    class Repositories {
        @Provides
        fun provideUserRepository(repository: FirestoreUserRepository): UserRepository = repository
    }
}

