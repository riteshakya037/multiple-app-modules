package com.riteshakya.teacher.data.datasource.geocode

import com.riteshakya.teacher.data.datasource.geocode.model.GeocodeResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeAPI {
    @GET("maps/api/geocode/json")
    fun getCityName(
            @Query("address") postalCode: String,
            @Query("sensor") sensor: Boolean = true,
            @Query("key") apiKey: String
    ): Single<GeocodeResponse>
}