package com.riteshakya.businesslogic.data.datasource.student

import com.riteshakya.businesslogic.data.datasource.student.repository.FirestoreStudentRepository
import com.riteshakya.businesslogic.repository.student.StudentRepository
import dagger.Module
import dagger.Provides

class StudentModule {

    @Module
    class Repositories {
        @Provides
        fun provideStudentRepository(
            repository: FirestoreStudentRepository
        ): StudentRepository = repository
    }
}

