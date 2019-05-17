package com.riteshakya.teacher.data.datasource.user.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.riteshakya.core.contants.DatabaseName
import com.riteshakya.core.model.BaseUser
import com.riteshakya.teacher.data.datasource.school.model.SchoolDto
import com.riteshakya.teacher.repository.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirestoreUserRepository
@Inject constructor(

) : UserRepository {
    private val userCollection by lazy { FirebaseFirestore.getInstance().collection(DatabaseName.TABLE_USERS) }

    override fun createUser(userId: String, user: BaseUser): Completable {
        return Completable.create {
            userCollection.document(userId).set(user)
            it.onComplete()
        }
    }

    override fun getSchoolUser(userId: String): Single<SchoolDto> {
        return Single.create { emitter ->
            userCollection.document(userId).get().addOnSuccessListener { documentSnapshot ->
                try {
                    val toObject = documentSnapshot.toObject(SchoolDto::class.java)
                    emitter.onSuccess(toObject!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}