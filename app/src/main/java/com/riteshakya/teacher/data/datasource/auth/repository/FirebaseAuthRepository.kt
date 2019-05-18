package com.riteshakya.teacher.data.datasource.auth.repository

import com.google.firebase.auth.FirebaseAuth
import com.riteshakya.core.exception.AuthenticationException
import com.riteshakya.core.exception.SignUpException
import com.riteshakya.core.exception.UserNotAuthenticatedException
import com.riteshakya.core.model.BaseUser
import com.riteshakya.teacher.repository.auth.AuthRepository
import com.riteshakya.teacher.repository.user.UserRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthRepository
@Inject constructor(
        private val userRepository: UserRepository
) : AuthRepository {

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    override fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    override fun logoutUser(): Completable {
        return Completable.create {
            auth.signOut()
            it.onComplete()
        }
    }

    override fun getUser(): Single<BaseUser> {
        return if (isLoggedIn())
            userRepository.getUser(auth.currentUser!!.uid)
        else
            Single.error(UserNotAuthenticatedException())
    }

    override fun createAuth(phoneNo: String, school: String, password: String): Single<String> {
        return Single.create<String> { emitter ->
            auth.createUserWithEmailAndPassword(createEmail(phoneNo, school), password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            it.result?.let { result ->
                                emitter.onSuccess(result.user.uid)
                            }
                        } else {
                            emitter.onError(SignUpException())
                        }
                    }
        }
    }

    override fun loginUser(phoneNo: String, school: String, password: String): Completable {
        return Completable.create { emitter ->
            auth.signInWithEmailAndPassword(createEmail(phoneNo, school), password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            auth.currentUser?.let {
                                emitter.onComplete()
                            }
                        } else {
                            it.exception?.also { e ->
                                emitter.onError(AuthenticationException(e.localizedMessage))
                            }
                        }
                    }
        }
    }

    private fun createEmail(phoneNo: String, school: String): String {
        return "$phoneNo@$school.com"
    }

}