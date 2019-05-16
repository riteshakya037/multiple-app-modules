package com.riteshakya.ui.adapters.base

import com.riteshakya.core.model.BaseModel


open class GenericListAdapter<T : BaseModel>
constructor(

) : BaseListAdapter<T, BindableViewHolder<T>>()
