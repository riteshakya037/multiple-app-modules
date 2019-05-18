package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import com.mukesh.countrypicker.Country
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * @author Ritesh Shakya
 */

class CountrySpinner : AppCompatSpinner, AdapterView.OnItemSelectedListener {
    var dialCode: String = ""
        set(value) {
            field = value
            setSelection(customAdapter.getPosition(getCountryFromDialCode(value)))
            publishValidity.onNext(true)
        }

    private var customAdapter: CountryAdapter = CountryAdapter(context)
    private var publishValidity = PublishSubject.create<Boolean>()

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

    private fun getCountryFromDialCode(value: String): Country =
            Country.getAllCountries().first { it.dialCode == value }


    override fun onItemSelected(adapterView: AdapterView<*>, view: View?, i: Int, l: Long) {
        dialCode = customAdapter.getDialCode(i)
        publishValidity.onNext(true)
    }

    override fun onNothingSelected(adapterView: AdapterView<*>) {

    }

    fun addValidity(): Observable<Boolean> {
        return publishValidity
    }
}