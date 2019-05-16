package com.riteshakya.ui.helpers

import com.riteshakya.ui.components.TextInput

class EqualToOtherFieldValidation(
        otherField: TextInput,
        reason: String = ""
) : com.riteshakya.core.validation.types.EqualToOtherFieldValidation(
        otherField.getEditText(), reason
)