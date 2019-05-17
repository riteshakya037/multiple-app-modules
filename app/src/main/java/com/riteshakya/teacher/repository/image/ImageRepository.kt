package com.riteshakya.teacher.repository.image

import io.reactivex.Single

interface ImageRepository {
    fun uploadProfileImage(path: String, userId: String): Single<String>
    fun uploadLogoImage(path: String, schoolId: String): Single<String>
}