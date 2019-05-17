package com.riteshakya.core.validation.types


import android.widget.EditText
import com.riteshakya.core.validation.Validation
import com.riteshakya.core.validation.ValidationResult

/**
 * @author Ritesh Shakya
 */

open class EqualToOtherFieldValidation(
        private val otherField: EditText, private val reason: String = ""
) : Validation {

    override fun validate(s: String): ValidationResult<Any> {
        return if (s.isNotEmpty() && otherField.text.toString() == s) {
            ValidationResult.success(s)
        } else {
            ValidationResult.failure(reason, s)
        }
    }
}
