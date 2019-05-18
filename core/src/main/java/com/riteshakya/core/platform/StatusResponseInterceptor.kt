package com.riteshakya.core.platform

import com.riteshakya.core.exception.StatusResponseException
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject


class StatusResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        try {
            val responseBody = JSONObject(response.body()!!.string())
            if (responseBody.has("status") && responseBody.getString("status") != "OK") {
                throw StatusResponseException(responseBody.getString("error_message"))
            }
        } catch (e: JSONException) {
        }
        return response
    }
}
