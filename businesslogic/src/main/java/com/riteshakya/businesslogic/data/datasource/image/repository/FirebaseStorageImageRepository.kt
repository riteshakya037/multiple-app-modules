package com.riteshakya.businesslogic.data.datasource.image.repository

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.riteshakya.businesslogic.repository.image.ImageRepository
import com.riteshakya.businesslogic.utils.upload
import com.riteshakya.core.contants.DatabaseName
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseStorageImageRepository
@Inject constructor(

) : ImageRepository {
    private val userProfileStorageRef: StorageReference by lazy {
        FirebaseStorage.getInstance().reference.child(DatabaseName.STORAGE_PROFILE_UPLOADS)
    }

    private val schoolLogoStorageRef: StorageReference by lazy {
        FirebaseStorage.getInstance().reference.child(DatabaseName.STORAGE_LOGO_UPLOADS)
    }

    override fun uploadProfileImage(path: String, userId: String): Single<String> {
        val profileStorage = userProfileStorageRef.child("$userId/${DatabaseName.FILE_PROFILE}")
        return profileStorage.upload(path)
    }

    override fun uploadLogoImage(path: String, schoolId: String): Single<String> {
        val logoStorage = schoolLogoStorageRef.child("$schoolId/${DatabaseName.FILE_LOGO}")
        return logoStorage.upload(path)
    }

}
