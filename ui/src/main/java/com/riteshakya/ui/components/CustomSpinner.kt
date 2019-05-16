package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatSpinner
import com.riteshakya.ui.R


/**
 * @author Ritesh Shakya
 */

class CustomSpinner @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatSpinner(context, attrs, defStyleAttr), AdapterView.OnItemSelectedListener {
    @StyleRes
    private var selectedTextAppearance: Int = R.style.TextAppearance_TextInput_Text
        set(value) {
            spinnerAdapter.selectedTextAppearance = (value)
            field = value
        }
    private var entries: Array<out CharSequence>? = null
        set(value) {
            field = value
            value?.also {
                setItems(it.map { item -> item.toString() }.map { item ->
                    SpinnerAdapter.SpinnerModel(
                            item, item
                    )
                })
            }
        }


    var selectedValue: String? = null
    private var spinnerAdapter: SpinnerAdapter = SpinnerAdapter(context)
    private var selectedListener: ((String) -> Unit)? = null

    init {
        init()
        initTypedArray(attrs)
    }


    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSpinner, 0, 0)
        entries = ta.getTextArray(R.styleable.CustomSpinner_android_entries)
        selectedTextAppearance = ta.getResourceId(
                R.styleable.CustomSpinner_selectedTextAppearance,
                R.style.TextAppearance_TextInput_Text
        )
        ta.recycle()
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