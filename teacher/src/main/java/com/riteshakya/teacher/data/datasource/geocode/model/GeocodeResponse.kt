package com.riteshakya.teacher.data.datasource.geocode.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.riteshakya.core.model.StatusResponse

class GeocodeResponse : StatusResponse() {

    @SerializedName("results")
    @Expose
    var results: List<Result> = arrayListOf()
    @SerializedName("formatted_address")
    @Expose
    var formattedAddress: String = ""

}
