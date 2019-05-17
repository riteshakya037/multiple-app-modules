package com.riteshakya.teacher.data.datasource.image

import com.riteshakya.teacher.data.datasource.image.repository.FirebaseStorageImageRepository
import com.riteshakya.teacher.repository.image.ImageRepository
import dagger.Module
import dagger.Provides

class ImageModule {

    @Module
    class Repositories {
        @Provides
        fun provideImageRepository(
                repository: FirebaseStorageImageRepository
        ): ImageRepository = repository
    }
}

