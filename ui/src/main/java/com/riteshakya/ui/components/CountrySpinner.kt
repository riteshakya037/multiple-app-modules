package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import com.mukesh.countrypicker.Country

/**
 * @author Ritesh Shakya
 */

class CountrySpinner : AppCompatSpinner, AdapterView.OnItemSelectedListener {
    var dialCode: String = ""
    private var customAdapter: CountryAdapter = CountryAdapter(context)
    private var selectedListener: ((String) -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
            context, attrs, defStyleAttr
    ) {
        init()
    }

    private fun init() {
        adapter = customAdapter
        if (!isInEditMode) {
            setSelection(customAdapter.getPosition(Country.getCountryFromSIM(context)))
        }
        onItemSelectedListener = this
    }

    fun onCodeChange(selectedListener: (dialCode: String) -> Unit) {
        this.selectedListener = selectedListener
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
        dialCode = customAdapter.getDialCode(i)
        selectedListener?.invoke(dialCode)
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {

    }
}