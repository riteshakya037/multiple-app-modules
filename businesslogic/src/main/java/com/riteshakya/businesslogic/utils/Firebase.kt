package com.riteshakya.businesslogic.utils

import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import io.reactivex.Single

fun StorageReference.upload(path: String): Single<String> {
    if (path.contains("https") || path.isEmpty()) {
        return Single.just(path)
    } else {
        val file = Uri.fromFile(java.io.File(path))
        return Single.create {
            putFile(file).continueWithTask(
                    Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let { exception ->
                                it.onError(exception)
                            }
                        }
                        return@Continuation downloadUrl
                    }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    it.onSuccess(task.result!!.toString())
                } else {
                    task.exception?.let { exception ->
                        it.onError(exception)
                    }
                }
            }
        }
    }
}
