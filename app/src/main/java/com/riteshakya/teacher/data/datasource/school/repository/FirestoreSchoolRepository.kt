package com.riteshakya.teacher.data.datasource.school.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.core.contants.DatabaseName
import com.riteshakya.core.contants.DatabaseName.Companion.FIELD_USER_ID
import com.riteshakya.core.exception.ErrorFetching
import com.riteshakya.teacher.data.datasource.school.model.SchoolDto
import com.riteshakya.teacher.data.datasource.school.model.transform
import com.riteshakya.teacher.repository.auth.AuthRepository
import com.riteshakya.teacher.repository.image.ImageRepository
import com.riteshakya.teacher.repository.school.SchoolRepository
import com.riteshakya.teacher.repository.school.model.SchoolModel
import com.riteshakya.teacher.repository.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
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

    private val schoolCollection by lazy { FirebaseFirestore.getInstance().collection(DatabaseName.TABLE_SCHOOLS) }

    override fun createSchool(school: SchoolModel): Completable {
        val document = schoolCollection.document()
        return authRepository.createAuth(school.phoneNo.fullNumber, document.id, school.password)
            .flatMapCompletable { userId ->
                uploadImages(school, userId, document.id)
                    .flatMapCompletable { schoolDto ->
                        userRepository.createUser(userId, schoolDto)
                            .andThen {
                                val data = mapOf(
                                    FIELD_USER_ID to userId
                                )
                                document.set(data)
                                it.onComplete()
                            }
                    }
        }
    }

    override fun getSchoolList(): Single<List<SchoolModel>> {
        return Single.create(fun(emitter: SingleEmitter<List<SchoolWrapper>>) {
            schoolCollection.get().addOnCompleteListener {
                if (it.isSuccessful) {
                    emitter.onSuccess(it.result!!.documents.map { school ->
                        SchoolWrapper(
                            school.id,
                            school[FIELD_USER_ID] as String
                        )
                    })
                } else {
                    emitter.onError(ErrorFetching("Schools"))
                }
            }
        }).flatMap {
            Observable.fromIterable(it).flatMapSingle { wrapper ->
                userRepository.getSchoolUser(wrapper.userId).map { school ->
                    school.transform(wrapper.schoolId)
                }
            }.toList()
        }
    }

    data class SchoolWrapper(val schoolId: String, val userId: String)

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