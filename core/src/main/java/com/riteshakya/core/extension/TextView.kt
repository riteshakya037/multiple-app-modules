package com.riteshakya.core.extension

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.widget.EditText
import android.widget.TextView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.riteshakya.core.validation.Validation
import com.riteshakya.core.validation.ValidationResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

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

fun EditText.addValidity(
    validation: Validation, changedText: (String) -> Unit = {}
): Observable<Boolean> {
    return RxTextView.textChanges(this)
        .skipInitialValue()
        .debounce(200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .map {
            changedText(it.toString())
            validation.validate(it.toString())
        }
        .map { result: ValidationResult<Any> -> result.isValid }

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