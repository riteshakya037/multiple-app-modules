package com.riteshakya.teacher.data.datasource.geocode.repository

import com.riteshakya.teacher.data.datasource.geocode.GeocodeService
import com.riteshakya.teacher.repository.geocode.GeocodeRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class GoogleGeocodeRepository
@Inject constructor(
        private var geocodeService: GeocodeService
) : GeocodeRepository {

    override fun getCityName(postalCode: String): Single<String> {
        return geocodeService.getCityName(postalCode)
                .subscribeOn(Schedulers.io())
                .map {
                    it.formattedAddress
                }
    }
}