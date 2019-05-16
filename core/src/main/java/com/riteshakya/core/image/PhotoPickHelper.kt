package com.riteshakya.core.image

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * author riteshakya037
 */

class PhotoPickHelper
@Inject constructor(
        private val context: Context,
        private val permissionHelper: PermissionHelper,
        private val realPathUtil: RealPathUtil
) {
    private var tempPhotoPath: String? = null
    private var photoPickCallback: PhotoPickCallback = object :
            PhotoPickCallback {
        override fun showError(b: Boolean, e: Throwable) {
            e.printStackTrace()
        }

        override fun setUpImage(currentPhotoPath: String) {
            Timber.e(currentPhotoPath)
        }
    }
    private var activity: Activity? = null
    private var fragment: Fragment? = null

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
        )
        tempPhotoPath = image.absolutePath
        return image
    }

    fun requestTakePhoto(applicationId: String) {
        permissionHelper.checkPermission(
                Manifest.permission.CAMERA,
                "Camera Permission",
                "Permission is needed to take a photo",
                object : PermissionHelper.PermissionGrant {
                    override fun permissionGranted() {
                        val takePictureIntent = Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE
                        )
                        // Ensure that there's a camera context to handle the intent
                        if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                            // Create the File where the photo should go
                            var photoFile: File? = null
                            try {
                                photoFile = createImageFile()
                            } catch (ex: IOException) {
                                photoPickCallback.showError(true, ex)
                                // Error occurred while creating the File
                            }

                            // Continue only if the File was successfully created
                            if (photoFile != null) {
                                val photoURI =
                                        FileProvider.getUriForFile(
                                                context, "$applicationId.provider", photoFile
                                        )
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                if (fragment != null) {
                                    fragment?.startActivityForResult(
                                            takePictureIntent,
                                            REQUEST_TAKE_PHOTO
                                    )
                                } else if (activity != null) {
                                    activity?.startActivityForResult(
                                            takePictureIntent,
                                            REQUEST_TAKE_PHOTO
                                    )
                                }
                            }
                        }
                    }
                })
    }

    fun requestPickPhoto() {
        permissionHelper.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                "Storage Permission",
                "Permission is needed to read the image files",
                object : PermissionHelper.PermissionGrant {
                    override fun permissionGranted() {
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        if (fragment != null) {
                            fragment!!.startActivityForResult(
                                    Intent.createChooser(intent, "Complete action using"),
                                    REQUEST_PICK_IMAGE
                            )
                        } else if (activity != null) {
                            activity!!.startActivityForResult(
                                    Intent.createChooser(intent, "Complete action using"),
                                    REQUEST_PICK_IMAGE
                            )
                        }
                    }
                })
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_PICK_IMAGE) {
            data ?: return
            if (resultCode == RESULT_OK && data.data != null) {
                val currentPhotoPath = realPathUtil.getRealPath(data.data!!)
                photoPickCallback.setUpImage(currentPhotoPath)
            }
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoPickCallback.setUpImage(tempPhotoPath ?: "")
        }
    }

    fun addPhotoPickCallback(photoPickCallback: PhotoPickCallback) {
        this.photoPickCallback = photoPickCallback
    }

    fun setActivity(activity: Activity) {
        this.activity = activity
        permissionHelper.setActivity(activity)
    }

    fun setFragment(fragment: Fragment) {
        this.fragment = fragment
        permissionHelper.setActivity(Objects.requireNonNull<FragmentActivity>(fragment.activity))
    }

    interface PhotoPickCallback {
        fun showError(b: Boolean, e: Throwable)

        fun setUpImage(currentPhotoPath: String)
    }

    companion object {
        const val REQUEST_TAKE_PHOTO = 1
        const val REQUEST_PICK_IMAGE = 542
    }
}
