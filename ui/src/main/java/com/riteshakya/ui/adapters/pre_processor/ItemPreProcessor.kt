package com.riteshakya.ui.adapters.pre_processor

import androidx.annotation.NonNull
import com.riteshakya.core.model.BaseModel

/**
 * @author Ritesh Shakya
 */

interface ItemPreProcessor<T : BaseModel> {
    fun doProcess(@NonNull input: List<T>): List<T>
}
