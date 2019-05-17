package com.riteshakya.core.model

data class PhoneModel(val dialCode: String = "", val phoneNo: String = "") {
    val fullNumber
        get() = dialCode + phoneNo
}
