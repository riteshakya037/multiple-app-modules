package com.riteshakya.teacher.data.datasource.geocode

import com.riteshakya.teacher.data.datasource.geocode.repository.GoogleGeocodeRepository
import com.riteshakya.teacher.repository.geocode.GeocodeRepository
import dagger.Module
import dagger.Provides

class GeocodeModule {
    @Module
    class Repositories {
        @Provides
        fun provideGeoCodeRepository(
            repository: GoogleGeocodeRepository
        ): GeocodeRepository = repository
    }
}