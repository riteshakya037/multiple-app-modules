package com.riteshakya.businesslogic.data.datasource.school

import com.riteshakya.businesslogic.data.datasource.school.repository.FirestoreSchoolRepository
import com.riteshakya.businesslogic.repository.school.SchoolRepository
import dagger.Module
import dagger.Provides

class SchoolModule {

    @Module
    class Repositories {
        @Provides
        fun provideSchoolRepository(
                repository: FirestoreSchoolRepository
        ): SchoolRepository = repository
    }
}

