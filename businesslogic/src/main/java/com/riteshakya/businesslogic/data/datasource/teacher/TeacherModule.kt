package com.riteshakya.businesslogic.data.datasource.teacher

import com.riteshakya.businesslogic.data.datasource.teacher.repository.FirestoreTeacherRepository
import com.riteshakya.businesslogic.repository.teacher.TeacherRepository
import dagger.Module
import dagger.Provides

class TeacherModule {

    @Module
    class Repositories {
        @Provides
        fun provideTeacherRepository(
                repository: FirestoreTeacherRepository
        ): TeacherRepository = repository
    }
}

