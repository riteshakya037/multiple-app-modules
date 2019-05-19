package com.riteshakya.ui.components

import android.content.Context
import android.os.Handler
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

class CountrySpinner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatSpinner(context, attrs, defStyleAttr), AdapterView.OnItemSelectedListener {
    var dialCode: String = ""
        set(value) {
            field = value
            setSelection(customAdapter.getPosition(getCountryFromDialCode(value)))
            publishValidity.onNext(true)
        }

    private val customAdapter: CountryAdapter by lazy { CountryAdapter(context) }
    private val publishValidity = PublishSubject.create<Boolean>()

    override fun onFinishInflate() {
        super.onFinishInflate()
        Handler().postDelayed({
            adapter = customAdapter
            if (!isInEditMode) {
                setSelection(customAdapter.getPosition(Country.getCountryFromSIM(context)))
            }
            onItemSelectedListener = this
        }, 200)
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