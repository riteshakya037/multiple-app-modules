package com.riteshakya.ui.adapters.base

import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import com.riteshakya.core.model.BaseModel
import io.reactivex.subjects.PublishSubject
import kotlin.reflect.KClass

abstract class BaseListAdapter<T : BaseModel, H : BindableViewHolder<T>> : BaseAdapter<H>() {

    private val items: MutableList<T> = ArrayList()
    private var tempItems: MutableList<T> = ArrayList()
    private val mFactoryResolver = ViewHolderFactoryResolver<BindableViewHolder<*>>()
    private val publish: PublishSubject<T> = PublishSubject.create()
    private val publishList: PublishSubject<List<T>> = PublishSubject.create()

    open fun <K : T> registerViewHolderFactory(
            type: KClass<K>,
            layout: Int,
            bindViewHolder: (View, ViewGroup) -> BindableViewHolder<K>,
            onClick: (K) -> Unit = {}
    ) {
        val factory = object : ViewHolderFactory<BindableViewHolder<K>>(layout) {
            override fun createView(itemView: View, parent: ViewGroup): BindableViewHolder<K> {
                return bindViewHolder(itemView, parent)
            }
        }
        mFactoryResolver.registerFactory(type, factory)

        manage(publish.filter { it.javaClass.kotlin == type }.map {
            @Suppress("UNCHECKED_CAST")
            it as K
        }.subscribe {
            onClick(it)
        })
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): H {
        @Suppress("UNCHECKED_CAST")
        return mFactoryResolver.getFactoryForId(viewType).create(parent) as H
    }

    @Suppress("UNCHECKED_CAST")
    @CallSuper
    override fun onBindViewHolder(holder: H, position: Int) {
        val item: T = tempItems[position]
        holder.listSize = tempItems.size
        (holder as BindableViewHolder<BaseModel>).bindItem(item)
        holder.itemView.setOnClickListener {
            setOnClick(item, holder, position)
        }
    }

    @CallSuper
    open fun setOnClick(item: T, holder: H, position: Int) {
        publish.onNext(item)
    }

    override fun getItemCount(): Int = tempItems.size


    open fun setItems(items: List<T>) {
        this.items.clear()
        val processedMessages = items
        this.items.addAll(processedMessages)

        this.tempItems = this.items
        publishList.onNext(this.items)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        val neModel = tempItems[position]
        return mFactoryResolver.getIdForType(neModel::class)
    }

    fun onDataRemoved(index: Int) {
        this.items.removeAt(index)
        this.tempItems = this.items
        publishList.onNext(this.items)
        notifyItemRemoved(index)
    }

}
