package com.riteshakya.teacher.data.datasource.geocode.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AddressComponent {

    @SerializedName("long_name")
    @Expose
    var longName: String = ""
    @SerializedName("short_name")
    @Expose
    var shortName: String = ""

}
