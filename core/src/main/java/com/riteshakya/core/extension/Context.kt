package com.riteshakya.core.extension

import android.content.Context
import android.net.ConnectivityManager

val Context.isConnected: Boolean
    get() =
        connectivityManager.activeNetworkInfo.let {
            it != null && it.isConnected
        }

val Context.connectivityManager: ConnectivityManager
    get() = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


val Context.screenWidth: Int
    get() = resources.displayMetrics.widthPixels

fun Context.getDimensionPixelSize(dimen: Int): Int = resources.getDimensionPixelSize(dimen)