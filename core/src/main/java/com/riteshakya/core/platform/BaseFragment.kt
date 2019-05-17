package com.riteshakya.core.platform

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.extension.combineLatest
import com.riteshakya.core.rx.DisposeOnLifecycleFragment
import com.riteshakya.core.rx.LifecycleDisposables
import dagger.android.support.DaggerFragment
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

abstract class BaseFragment : DaggerFragment(), BackPressConsumer, DisposeOnLifecycleFragment {
    override val lifecycleDisposables = LifecycleDisposables()

    override fun onPause() {
        super<DaggerFragment>.onPause()
        super<DisposeOnLifecycleFragment>.onPause()
    }

    override fun onStop() {
        super<DaggerFragment>.onStop()
        super<DisposeOnLifecycleFragment>.onStop()
    }

    override fun onDestroy() {
        super<DaggerFragment>.onDestroy()
        super<DisposeOnLifecycleFragment>.onDestroyView()
    }

    override fun onDestroyView() {
        super<DaggerFragment>.onDestroyView()
        super<DisposeOnLifecycleFragment>.onDestroy()
    }

    private val navController: NavController by lazy {
        NavHostFragment.findNavController(this)
    }

    private fun hasNavController(): Boolean {
        return try {
            NavHostFragment.findNavController(this)
            true
        } catch (e: IllegalStateException) {
            false
        }
    }

    private var observerList: ArrayList<Observable<Boolean>> = ArrayList()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetValidationList()
    }

    protected fun resetValidationList() {
        observerList.clear()
        if (::subscribe.isInitialized && !subscribe.isDisposed) {
            subscribe.dispose()
        }
        setValidity(false)
    }

    protected fun addValidationList(addValidity: Observable<Boolean>) {
        observerList.add(addValidity)
        initializeValidationObservers()
    }

    private lateinit var subscribe: Disposable

    private fun initializeValidationObservers() {
        if (::subscribe.isInitialized && !subscribe.isDisposed) {
            subscribe.dispose()
        }
        subscribe = observerList.combineLatest {
            var output = true
            Timber.e(it.toString())
            for (result: Boolean in it)
                if (!result && output) {
                    output = false
                }
            output
        }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> setValidity(result) }
    }

    open fun setValidity(result: Boolean) {}

    private var progressDialog: LoadingUiHelper.ProgressDialogFragment? = null

    fun showLoading(type: LoadingUiHelper.Type = LoadingUiHelper.Type.DIALOG) {
        if (progressDialog == null) {
            progressDialog = LoadingUiHelper.showProgress(fragmentManager!!, type)
        }
    }

    fun <T> Single<T>.addLoading(): Single<T> {
        return doOnSubscribe {
            showLoading()
        }.doOnSuccess {
            hideLoading()
        }.subscribeOn(Schedulers.io())
    }

    fun Completable.addLoading(): Completable {
        return doOnSubscribe {
            showLoading()
        }.doOnComplete {
            hideLoading()
        }.subscribeOn(Schedulers.io())
    }

    fun <T> Observable<T>.addLoading(): Observable<T> {
        return doOnSubscribe {
            showLoading()
        }.doOnNext {
            hideLoading()
        }.subscribeOn(Schedulers.io())
    }

    fun hideLoading() {
        progressDialog?.dismiss()
        progressDialog = null
    }

    override fun consumeBackPressed(): Boolean {
        return when {
            hasNavController() -> navController.navigateUp()
            else -> false
        }
    }
}