package com.riteshakya.ui.adapters.base

import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseAdapter<T : BindableViewHolder<*>> : RecyclerView.Adapter<T>() {

    private val disposables = CompositeDisposable()

    fun manage(disposable: Disposable) {
        this.disposables.add(disposable)
    }

    @CallSuper
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        disposables.dispose()
    }

    override fun onViewRecycled(holder: T) {
        super.onViewRecycled(holder)
        holder.onViewRecycled()
    }
}
