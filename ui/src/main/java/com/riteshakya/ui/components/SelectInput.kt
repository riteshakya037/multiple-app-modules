package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.ui.R
import com.riteshakya.ui.helpers.indexOf
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_select_input.view.*

class SelectInput @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var publishValidity = PublishSubject.create<Boolean>()
    private var selectedValue: String? = null
    var hint: String = ""
        set(value) {
            hintTxt.text = value
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

    var items: List<SpinnerAdapter.SpinnerModel> = emptyList()
        get() = spinner.items
        set(value) {
            spinner.items = value
            field = value
            if (!selectedValue.isNullOrBlank()) {
                setSelection(selectedValue)
            }
        }

    init {
        init()
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.SelectInput, 0, 0)
        hint = ta.getString(R.styleable.SelectInput_android_hint) ?: ""
        entries = ta.getTextArray(R.styleable.SelectInput_android_entries)

        ta.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_select_input, this)
        spinner.onItemChanged {
            selectedValue = it
            publishValidity.onNext(true)
        }
    }

    fun addValidity(listener: (String) -> Unit = {}): Observable<Boolean> {
        return publishValidity.doOnNext {
            selectedValue?.let { listener(it) }
        }
    }

    fun setSelection(item: String?) {
        this.selectedValue = item
        item?.let {
            spinner.setSelection(spinner.items.indexOf(it))
            publishValidity.onNext(true)
        }
    }

}