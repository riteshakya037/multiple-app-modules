package com.riteshakya.teacher.data.datasource.user

import com.riteshakya.teacher.data.datasource.user.repository.FirestoreUserRepository
import com.riteshakya.teacher.repository.user.UserRepository
import dagger.Module
import dagger.Provides

class UserModule {

    @Module
    class Repositories {
        @Provides
        fun provideUserRepository(repository: FirestoreUserRepository): UserRepository = repository
    }
}

