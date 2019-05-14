package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner

/**
 * @author Ritesh Shakya
 */

class CustomSpinner : AppCompatSpinner, AdapterView.OnItemSelectedListener {
    var selectedValue: String? = null
    private var spinnerAdapter: SpinnerAdapter = SpinnerAdapter(context)
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
        adapter = spinnerAdapter
        onItemSelectedListener = this
    }

    fun onItemChanged(selectedListener: (dialCode: String) -> Unit) {
        this.selectedListener = selectedListener
    }

    fun setItems(items: List<SpinnerAdapter.SpinnerModel>) {
        spinnerAdapter.setItems(items)
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        selectedValue = spinnerAdapter.getValue(i)
        selectedListener?.invoke(selectedValue!!)
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {

    }
}