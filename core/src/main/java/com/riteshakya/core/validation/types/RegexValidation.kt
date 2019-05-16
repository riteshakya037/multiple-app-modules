package com.riteshakya.core.validation.types

import com.riteshakya.core.validation.Validation
import com.riteshakya.core.validation.ValidationResult
import java.util.regex.Pattern

open class RegexValidation(private val regex: String, private val reason: String) : Validation {

    override fun validate(s: String): ValidationResult<Any> {
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(s)
        val isValid = matcher.matches()
        return if (isValid) ValidationResult.success(s) else ValidationResult.failure(reason, s)
    }
}

class PasswordValidation(reason: String = "") : RegexValidation(
        "^(?=.*\\d)[A-Za-z\\d#\$@!%&*?]{6,}\$", reason
)

class NameValidation(reason: String = "") : RegexValidation(
        "(?i)^(?:[a-z]+(?: |\\. ?)?)+[a-z]$", reason
)

class EmailValidation(reason: String = "") : RegexValidation(
        "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\" + ".[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$",
        reason
)
