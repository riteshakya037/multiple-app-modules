package com.riteshakya.core.image

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.MimeTypeMap
import java.io.File
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * author riteshakya037
 */

class RealPathUtil
@Inject constructor(
        private val context: Context
) {

    fun getRealPath(fileUri: Uri): String {
        return getImagePathFromInputStreamUri(fileUri)
    }

    private fun getImagePathFromInputStreamUri(uri: Uri): String {
        var inputStream: InputStream? = null
        var filePath = ""

        if (uri.authority != null) {
            try {
                inputStream = context.contentResolver.openInputStream(uri) // context needed
                val cR = context.contentResolver
                val mime = MimeTypeMap.getSingleton()
                val type = mime.getExtensionFromMimeType(cR.getType(uri))
                val photoFile = createTemporalFileFrom(inputStream, uri.lastPathSegment, type)

                filePath = photoFile!!.absolutePath
            } catch (e: IOException) {
                // log
            } finally {
                try {
                    inputStream?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        return filePath
    }

    @Throws(IOException::class)
    private fun createTemporalFileFrom(
            inputStream: InputStream?, lastPathSegment: String?, type: String?
    ): File? {
        var targetFile: File? = null

        if (inputStream != null) {

            targetFile = createTemporalFile(lastPathSegment, type)
            val outputStream = targetFile.outputStream()
            inputStream.copyTo(outputStream, 8 * 1024)
            outputStream.flush()
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return targetFile
    }

    private fun createTemporalFile(lastPathSegment: String?, type: String?): File {
        return File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "tempFile$lastPathSegment.$type"
        )
    }
}