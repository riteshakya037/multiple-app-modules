package com.riteshakya.core.extension

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.TextView

fun TextView.onTextChanged(function: (String) -> Unit) {
    addTextChangedListener(object : SimpleTextWatcher() {
        var previousText: String = ""
        override fun afterTextChanged(chars: Editable?) {
            val currentString = chars.toString()
            if (previousText != currentString) {
                previousText = currentString
                function(currentString)
            }
        }
    })
}

abstract class SimpleTextWatcher : TextWatcher {
    override fun afterTextChanged(chars: Editable?) {
    }

    override fun beforeTextChanged(chars: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(chars: CharSequence?, start: Int, before: Int, count: Int) {
    }
}

fun TextView.addMovementMethod() {
    this.movementMethod = getInstance()
}

private var sInstance: LinkMovementMethod? = null

fun getInstance(): MovementMethod {
    if (sInstance == null)
        sInstance = LinkMovementMethod()

    return sInstance!!
}

fun TextView.setMaxLength(length: Int) {
    val filterArray = arrayOfNulls<InputFilter>(1)
    filterArray[0] = InputFilter.LengthFilter(length)
    filters = filterArray
}