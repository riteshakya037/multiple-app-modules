package com.riteshakya.teacher.repository.geocode

import io.reactivex.Single

interface GeocodeRepository {
    fun getCityName(postalCode: String): Single<String>
}