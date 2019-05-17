package com.riteshakya.core.image

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.os.Build
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import javax.inject.Inject

class PermissionHelper
@Inject constructor(
        private val context: Context
) {
    private var listener: PermissionListener? = null
    private var activity: Activity? = null

    fun checkPermission(
            permission: String, title: String, message: String, permissionGrant: PermissionGrant
    ) {
        listener = object : PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse) {
                permissionGrant.permissionGranted()
                listener = null
            }

            override fun onPermissionDenied(response: PermissionDeniedResponse) {
                DialogOnDeniedPermissionListener.Builder.withContext(context)
                        .withTitle(title)
                        .withMessage(message)
                        .withButtonText(android.R.string.ok)
                        .build()
                listener = null
            }

            override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest, token: PermissionToken
            ) {
                showPermissionRationale(token, title, message)
                listener = null
            }
        }
        if (this.activity == null) throw RuntimeException()
        Dexter.withActivity(activity).withPermission(permission).withListener(listener).check()
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun showPermissionRationale(token: PermissionToken, title: String, message: String) {
        AlertDialog.Builder(activity!!).setTitle(title).setMessage(message)
                .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                    dialog.dismiss()
                    token.cancelPermissionRequest()
                }.setPositiveButton(android.R.string.ok) { dialog, _ ->
                    dialog.dismiss()
                    token.continuePermissionRequest()
                }.setOnDismissListener { token.cancelPermissionRequest() }.show()
    }

    interface PermissionGrant {
        fun permissionGranted()
    }
}
