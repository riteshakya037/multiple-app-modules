package com.riteshakya.core.validation.types


import com.riteshakya.core.validation.Validation
import com.riteshakya.core.validation.ValidationResult

/**
 * @author Ritesh Shakya
 */

class PhoneValidation(private val length: Int, private val reason: String = "") : Validation {

    override fun validate(s: String): ValidationResult<Any> {
        return if (s.length >= length) {
            ValidationResult.success(s)
        } else {
            ValidationResult.failure(reason, s)
        }
    }
}