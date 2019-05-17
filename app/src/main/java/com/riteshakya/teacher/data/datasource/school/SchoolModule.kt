package com.riteshakya.teacher.data.datasource.school;

import com.riteshakya.teacher.data.datasource.school.repository.FirestoreSchoolRepository
import com.riteshakya.teacher.repository.school.SchoolRepository
import dagger.Module;
import dagger.Provides;

class SchoolModule {

    @Module
    class Repositories {
        @Provides
        fun provideSchoolRepository(repository: FirestoreSchoolRepository): SchoolRepository = repository
    }
}

