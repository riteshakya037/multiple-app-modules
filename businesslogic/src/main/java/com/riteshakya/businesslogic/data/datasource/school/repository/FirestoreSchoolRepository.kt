package com.riteshakya.businesslogic.data.datasource.school.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.businesslogic.data.datasource.school.model.SchoolCollectionDto
import com.riteshakya.businesslogic.data.datasource.school.model.SchoolDto
import com.riteshakya.businesslogic.data.datasource.school.model.transform
import com.riteshakya.businesslogic.repository.auth.AuthRepository
import com.riteshakya.businesslogic.repository.image.ImageRepository
import com.riteshakya.businesslogic.repository.school.SchoolRepository
import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import com.riteshakya.businesslogic.repository.user.UserRepository
import com.riteshakya.core.contants.DatabaseName
import com.riteshakya.core.exception.ErrorFetching
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreSchoolRepository
@Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository
) : SchoolRepository {

    private val schoolCollection by lazy {
        FirebaseFirestore.getInstance().collection(
                DatabaseName.TABLE_SCHOOLS
        )
    }

    override fun createSchool(school: SchoolModel): Completable {
        val document = schoolCollection.document()
        return authRepository.createAuth(school.phoneNo.fullNumber, document.id, school.password)
                .flatMapCompletable { userId ->
                    uploadImages(school, userId, document.id)
                            .flatMapCompletable { schoolDto ->
                                userRepository.createUser(userId, schoolDto)
                                        .andThen {
                                            document.set(schoolDto.transform(userId, document.id))
                                            it.onComplete()
                                        }
                            }
                }
    }

    override fun getSchool(schoolId: String): Single<SchoolModel> {
        return Single.create { emitter ->
            schoolCollection.document(schoolId).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    emitter.onSuccess(
                            it.result!!.toObject(SchoolCollectionDto::class.java)!!.transform()
                    )
                } else {
                    emitter.onError(ErrorFetching("School"))
                }
            }
        }
    }

    override fun getSchoolList(): Single<List<SchoolModel>> {
        return Single.create { emitter ->
            schoolCollection.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    emitter.onSuccess(it.result!!.map { school ->
                        school.toObject(SchoolCollectionDto::class.java).transform()
                    })
                } else {
                    emitter.onError(ErrorFetching("Schools"))
                }
            }
        }
    }

    private fun uploadImages(
            school: SchoolModel,
            userId: String,
            schoolId: String
    ): Single<SchoolDto> {
        return Single.zip(
                imageRepository.uploadProfileImage(school.profilePhoto, userId),
                imageRepository.uploadLogoImage(school.schoolLogo, schoolId),
                BiFunction<String, String, SchoolDto> { profilePhoto, schoolLogo ->
                    val schoolDto = school.transform()
                    schoolDto.profile_photo = profilePhoto
                    schoolDto.logo = schoolLogo
                    schoolDto
                })
    }
}