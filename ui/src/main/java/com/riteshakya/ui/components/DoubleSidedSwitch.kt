package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.ui.R
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_double_sided_switch.view.*

class DoubleSidedSwitch @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), Checkable {

    private var publishValidity = PublishSubject.create<Boolean>().also {
        it.onNext(true)
    }
    private var listener: ((Boolean) -> Unit)? = null


    private var text: String = ""
        set(value) {
            textTxt.text = value
            field = value
        }

    private var textOn: String = ""
        set(value) {
            rightTxt.text = value
            field = value
        }

    private var textOff: String = ""
        set(value) {
            leftTxt.text = value
            field = value
        }

    init {
        init()
        initTypedArray(attrs)
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_double_sided_switch, this)
        switchBtn.setOnCheckedChangeListener { _, isChecked ->
            listener?.invoke(isChecked)
            publishValidity.onNext(true)
        }
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.DoubleSidedSwitch, 0, 0)
        text = ta.getString(R.styleable.DoubleSidedSwitch_android_text) ?: ""
        textOn = ta.getString(R.styleable.DoubleSidedSwitch_android_textOn) ?: ""
        textOff = ta.getString(R.styleable.DoubleSidedSwitch_android_textOff) ?: ""
        ta.recycle()
    }

    fun setOnCheckedChangeListener(listener: (Boolean) -> Unit) {
        this.listener = listener
    }

    override fun isChecked(): Boolean {
        return !switchBtn.isChecked
    }

    override fun toggle() {
        switchBtn.toggle()
    }

    override fun setChecked(checked: Boolean) {
        switchBtn.isChecked = !checked
        publishValidity.onNext(true)
    }

    fun addValidity(listener: (Boolean) -> Unit = {}): Observable<Boolean> {
        return publishValidity.doOnNext {
            listener(isChecked)
        }
    }

}