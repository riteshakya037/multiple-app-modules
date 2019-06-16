package com.riteshakya.teacher.data.datasource.geocode.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {

    @SerializedName("address_components")
    @Expose
    var addressComponents: List<AddressComponent> = arrayListOf()
    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String = ""
}
