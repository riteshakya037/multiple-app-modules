package com.riteshakya.businesslogic.data.datasource.image

import com.riteshakya.businesslogic.data.datasource.image.repository.FirebaseStorageImageRepository
import com.riteshakya.businesslogic.repository.image.ImageRepository
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

