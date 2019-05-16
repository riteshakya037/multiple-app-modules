package com.riteshakya.core.validation

/**
 * @author Ritesh Shakya
 */

interface Validation {
    fun validate(s: String): ValidationResult<Any>
}
