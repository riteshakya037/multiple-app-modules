package com.riteshakya.ui.helpers

import android.content.res.Resources
import com.riteshakya.ui.components.SpinnerAdapter

fun <T : SpinnerAdapter.SpinnerModel> List<T>.indexOf(id: String): Int {
    this.mapIndexed { index, it -> if (it.value == id) return index }
    throw Resources.NotFoundException()
}