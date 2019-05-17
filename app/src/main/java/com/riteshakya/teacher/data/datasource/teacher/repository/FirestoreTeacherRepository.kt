package com.riteshakya.teacher.data.datasource.teacher.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.core.contants.DatabaseName
import com.riteshakya.teacher.data.datasource.teacher.model.TeacherDto
import com.riteshakya.teacher.data.datasource.teacher.model.transform
import com.riteshakya.teacher.repository.auth.AuthRepository
import com.riteshakya.teacher.repository.image.ImageRepository
import com.riteshakya.teacher.repository.teacher.TeacherRepository
import com.riteshakya.teacher.repository.teacher.model.TeacherModel
import com.riteshakya.teacher.repository.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreTeacherRepository
@Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository

) : TeacherRepository {

    private val teachersCollection by lazy {
        FirebaseFirestore.getInstance().collection(
                DatabaseName.TABLE_TEACHERS
        )
    }

    override fun createTeacher(teacher: TeacherModel): Completable {
        val document = teachersCollection.document()
        return authRepository.createAuth(
                teacher.phoneNo.fullNumber, teacher.school, teacher.password
        )
                .flatMapCompletable { userId ->
                    uploadImage(teacher, userId)
                            .flatMapCompletable { teacherDto ->
                                userRepository.createUser(userId, teacherDto)
                                        .andThen {
                                            document.set(teacherDto.transform(userId, document.id))
                                            it.onComplete()
                                        }
                            }
                }
    }

    private fun uploadImage(teacher: TeacherModel, userId: String): Single<TeacherDto> {
        return imageRepository.uploadProfileImage(teacher.profilePicture, userId).map {
            val teacherDto = teacher.transform()
            teacherDto.profile_picture = it
            teacherDto
        }
    }

}