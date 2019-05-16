package com.riteshakya.core.validation

/**
 * @author Ritesh Shakya
 */
class ValidationResult<out T> private constructor(
        val
        isValid: Boolean, val reason: String?, val data: T
) {
    companion object {

        fun <T> success(t: T): ValidationResult<T> {
            return ValidationResult(true, null, t)
        }

        fun <T> failure(reason: String?, t: T): ValidationResult<T> {
            return ValidationResult(false, reason, t)
        }
    }
}
