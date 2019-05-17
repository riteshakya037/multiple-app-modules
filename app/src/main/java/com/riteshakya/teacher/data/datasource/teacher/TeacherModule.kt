package com.riteshakya.teacher.data.datasource.teacher

import com.riteshakya.teacher.data.datasource.teacher.repository.FirestoreTeacherRepository
import com.riteshakya.teacher.repository.teacher.TeacherRepository
import dagger.Module
import dagger.Provides

class TeacherModule {

    @Module
    class Repositories {
        @Provides
        fun provideTeacherRepository(repository: FirestoreTeacherRepository): TeacherRepository = repository
    }
}

