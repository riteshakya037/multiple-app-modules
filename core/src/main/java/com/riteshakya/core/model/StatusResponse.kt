package com.riteshakya.core.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class StatusResponse {
    @SerializedName("status")
    @Expose
    var status: String = ""
    @SerializedName("error_message")
    @Expose
    var errorMessage: String = ""
}