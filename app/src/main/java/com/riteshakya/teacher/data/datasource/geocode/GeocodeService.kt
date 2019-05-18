package com.riteshakya.teacher.data.datasource.geocode

import android.content.Context
import com.riteshakya.teacher.R
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocodeService
@Inject constructor(
        retrofit: Retrofit,
        val context: Context
) {
    private val geocodeAPI by lazy { retrofit.create(GeocodeAPI::class.java) }

    fun getCityName(postalCode: String) = geocodeAPI.getCityName(
            postalCode = postalCode,
            apiKey = context.resources.getString(R.string.google_api_key)
    )
}
