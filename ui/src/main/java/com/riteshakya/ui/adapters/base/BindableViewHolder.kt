package com.riteshakya.ui.adapters.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Ritesh Shakya
 */

abstract class BindableViewHolder<T : Any>(itemView: View, val context: Context = itemView.context) : RecyclerView.ViewHolder(itemView) {
    private val disposables = CompositeDisposable()
    var listSize: Int = 0

    fun manage(disposable: Disposable) {
        this.disposables.add(disposable)
    }

    private lateinit var boundItem: T

    fun bindItem(item: T) {
        if (::boundItem.isInitialized && boundItem == item) {
            update(item)
        } else {
            bind(item)
        }
        boundItem = item
    }

    fun getBoundItem(): T = boundItem

    protected abstract fun bind(item: T)

    protected open fun update(item: T) {
        bind(item)
    }

    fun onViewRecycled() {
        disposables.clear()
    }
}
