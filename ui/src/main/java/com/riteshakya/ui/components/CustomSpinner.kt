package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatSpinner
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.indexOf
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Ritesh Shakya
 */

class CustomSpinner @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : AppCompatSpinner(context, attrs, defStyleAttr), AdapterView.OnItemSelectedListener {
    private var publishValidity = PublishSubject.create<Boolean>()

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
                items = (it.map { item -> item.toString() }.map { item ->
                    SpinnerAdapter.SpinnerModel(
                            item, item
                    )
                })
            }
        }


    private var selectedValue: String? = null

    var items: List<SpinnerAdapter.SpinnerModel> = ArrayList()
        get() = spinnerAdapter.items
        set(value) {
            spinnerAdapter.setItems(value)
            field = value
            if (!selectedValue.isNullOrBlank()) {
                setSelection(selectedValue)
            }
        }

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

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        selectedValue = spinnerAdapter.getValue(i)
        selectedListener?.invoke(selectedValue!!)
        publishValidity.onNext(true)
    }

    fun setSelection(item: String?) {
        this.selectedValue = item
        item?.let {
            setSelection(items.indexOf(it))
            publishValidity.onNext(true)
        }
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {

    }

    fun addValidity(listener: (String) -> Unit = {}): Observable<Boolean> {
        return publishValidity.doOnNext {
            selectedValue?.let { listener(it) }
        }
    }
}