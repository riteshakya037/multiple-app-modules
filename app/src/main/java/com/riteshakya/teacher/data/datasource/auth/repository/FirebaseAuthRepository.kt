package com.riteshakya.teacher.data.datasource.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.riteshakya.core.exception.SignUpException
import com.riteshakya.teacher.repository.auth.AuthRepository
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository
@Inject constructor(

) : AuthRepository {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun createAuth(phoneNo: String, school: String, password: String): Single<String> {
        return Single.create<String> { emitter ->
            auth.createUserWithEmailAndPassword(createEmail(phoneNo, school), password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        auth.currentUser?.let { user ->
                            emitter.onSuccess(user.uid)
                        }
                    } else {
                        emitter.onError(SignUpException())
                    }
                }
        }
    }

    private fun createEmail(phoneNo: String, school: String): String {
        return "$phoneNo@$school.com"
    }

}