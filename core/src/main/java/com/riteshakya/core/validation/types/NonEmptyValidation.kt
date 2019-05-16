package com.riteshakya.core.validation.types


import com.riteshakya.core.validation.Validation
import com.riteshakya.core.validation.ValidationResult
import android.text.TextUtils

/**
 * @author Ritesh Shakya
 */

class NonEmptyValidation(private val reason: String = "Non empty field") : Validation {

    override fun validate(s: String): ValidationResult<Any> {
        return if (!TextUtils.isEmpty(s.trim())) {
            ValidationResult.success(s)
        } else {
            ValidationResult.failure(reason, s)
        }
    }
}
