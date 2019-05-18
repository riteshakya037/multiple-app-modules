package com.riteshakya.core.exception

import android.content.Context
import com.riteshakya.core.R
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject

class FailureMessageMapper
@Inject constructor(
        val context: Context
) {
    operator fun invoke(throwable: Throwable): String =
            context.resources.let {
                when (throwable) {
                    is HttpException -> it.getString(R.string.error_response)
                    is StatusResponseException -> throwable.errorMessage
                    is NoConnectivityException, is SocketTimeoutException -> it.getString(
                            R.string.error_no_connection
                    )
                    else -> it.getString(R.string.error_unknown)
                }
            }
}