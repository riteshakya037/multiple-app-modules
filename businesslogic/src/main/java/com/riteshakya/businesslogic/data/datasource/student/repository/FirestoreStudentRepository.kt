package com.riteshakya.businesslogic.data.datasource.student.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.businesslogic.data.datasource.student.model.StudentDto
import com.riteshakya.businesslogic.data.datasource.student.model.transform
import com.riteshakya.businesslogic.repository.auth.AuthRepository
import com.riteshakya.businesslogic.repository.image.ImageRepository
import com.riteshakya.businesslogic.repository.student.StudentRepository
import com.riteshakya.businesslogic.repository.student.model.StudentModel
import com.riteshakya.businesslogic.repository.user.UserRepository
import com.riteshakya.core.contants.DatabaseName
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreStudentRepository
@Inject constructor(
        private val authRepository: AuthRepository,
        private val userRepository: UserRepository,
        private val imageRepository: ImageRepository

) : StudentRepository {

    private val studentsCollection by lazy {
        FirebaseFirestore.getInstance().collection(
                DatabaseName.TABLE_STUDENTS
        )
    }

    override fun createStudent(student: StudentModel): Completable {
        val document = studentsCollection.document()
        return authRepository.createAuth(
                student.phoneNo.fullNumber, student.school, student.password
        )
                .flatMapCompletable { userId ->
                    uploadImage(student, userId)
                            .flatMapCompletable { studentDto ->
                                userRepository.createUser(userId, studentDto)
                                        .andThen {
                                            document.set(studentDto.transform(userId, document.id))
                                            it.onComplete()
                                        }
                            }
                }
    }

    private fun uploadImage(student: StudentModel, userId: String): Single<StudentDto> {
        return imageRepository.uploadProfileImage(student.profilePicture, userId).map {
            val studentDto = student.transform()
            studentDto.profile_picture = it
            studentDto
        }
    }

}