package com.riteshakya.core.image

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import com.riteshakya.core.platform.BaseFragment
import javax.inject.Inject

abstract class PhotoFragment : BaseFragment(),
        PhotoPickHelper.PhotoPickCallback {
    @Inject
    lateinit var photoPickHelper: PhotoPickHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoPickHelper.addPhotoPickCallback(this)
        photoPickHelper.setFragment(this)
    }

    override fun showError(b: Boolean, e: Throwable) {
        e.printStackTrace()
    }

    @CallSuper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        photoPickHelper.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}