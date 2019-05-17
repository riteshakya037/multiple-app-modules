package com.riteshakya.teacher.data.datasource.user.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.core.contants.DatabaseName
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.model.ERROR
import com.riteshakya.core.model.SCHOOL
import com.riteshakya.core.model.TEACHER
import com.riteshakya.teacher.data.datasource.school.model.SchoolDto
import com.riteshakya.teacher.data.datasource.school.model.transform
import com.riteshakya.teacher.data.datasource.teacher.model.TeacherDto
import com.riteshakya.teacher.data.datasource.teacher.model.transform
import com.riteshakya.teacher.data.model.BaseUserDto
import com.riteshakya.teacher.repository.school.SchoolRepository
import com.riteshakya.teacher.repository.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class FirestoreUserRepository
@Inject constructor(
        private var schoolRepository: Provider<SchoolRepository>
) : UserRepository {
    private val userCollection by lazy {
        FirebaseFirestore.getInstance().collection(
                DatabaseName.TABLE_USERS
        )
    }

    override fun createUser(userId: String, user: BaseUserDto): Completable {
        return Completable.create {
            userCollection.document(userId).set(user)
            it.onComplete()
        }
    }

    override fun getUser(userId: String): Single<BaseUser> {
        return Single.create { emitter: SingleEmitter<DocumentSnapshot> ->
            userCollection.document(userId).get().addOnSuccessListener { documentSnapshot ->
                try {
                    emitter.onSuccess(documentSnapshot)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }.flatMap { parseBasedOnRole(it) }
    }

    private fun parseBasedOnRole(documentSnapshot: DocumentSnapshot): Single<BaseUser> {
        return when (documentSnapshot.data?.get("role") as String) {
            TEACHER -> {
                val teacherModel = documentSnapshot.toObject(TeacherDto::class.java)!!.transform()
                schoolRepository.get().getSchool(teacherModel.school).map {
                    teacherModel.schoolModel = it
                    teacherModel
                }
            }
            SCHOOL -> {
                Single.just(documentSnapshot.toObject(SchoolDto::class.java)!!.transform())
            }
            else -> {
                Single.just(BaseUser(ERROR))
            }
        }
    }
}