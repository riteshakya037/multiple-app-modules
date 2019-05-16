package com.riteshakya.ui.adapters.base

import android.view.View
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.reflect.KClass

/**
 * @author Ritesh Shakya
 */

class ViewHolderFactoryResolver<T : RecyclerView.ViewHolder>(private val mOffset: Int = 0) {
    private val mViewHolderCreatorsFromType = HashMap<KClass<*>, ViewHolderFactoryWithId>()
    private val mMessageViewHolderFactories = SparseArrayCompat<ViewHolderFactory<out T>>()

    fun registerFactory(type: KClass<*>, factory: ViewHolderFactory<out T>): Int {
        val id = mMessageViewHolderFactories.size() + mOffset
        mViewHolderCreatorsFromType[type] = ViewHolderFactoryWithId(id, factory)
        mMessageViewHolderFactories.put(id, factory)
        return id
    }

    fun getFactoryForId(id: Int): ViewHolderFactory<out T> {
        if (id < mOffset || id > mOffset + mMessageViewHolderFactories.size()) {
            throw IllegalArgumentException("No factory registered for view with id $id")
        }
        return mMessageViewHolderFactories.get(id)!!
    }

    fun getIdForType(type: KClass<*>): Int {
        val viewHolderCreatorWithId = mViewHolderCreatorsFromType[type]
                ?: throw IllegalStateException(
                        "No view holder getCallingIntent registered for $type"
                )
        return viewHolderCreatorWithId.id
    }

    private inner class ViewHolderFactoryWithId internal constructor(
            val id: Int, private val mFactory: ViewHolderFactory<out T>
    ) : ViewHolderFactory<T>(mFactory.layoutRes) {
        override fun createView(itemView: View, parent: ViewGroup): T {
            return mFactory.createView(itemView, parent)
        }
    }
}
