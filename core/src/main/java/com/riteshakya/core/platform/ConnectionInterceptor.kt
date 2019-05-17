package com.riteshakya.core.platform

import android.content.Context
import com.riteshakya.core.exception.NoConnectivityException
import com.riteshakya.core.extension.isConnected
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
            context.isConnected.let {
                if (it) chain.proceed(chain.request())
                else throw NoConnectivityException()
            }
}
