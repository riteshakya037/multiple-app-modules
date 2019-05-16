package com.riteshakya.ui.components

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.core.extension.changeLayoutParams
import com.riteshakya.core.extension.onTextChanged
import com.riteshakya.ui.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.custom_password_view.view.*
import java.util.*

/**
 * author riteshakya037
 */
class PasswordView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var viewList: MutableList<PasswordCharacterView> = ArrayList()
    var count = PublishSubject.create<Int>()
        internal set

    var length: Int = 0
        set(value) {
            field = value
            createPasswordViews(value)
        }


    var inputType: Int = InputType.TYPE_NULL
        set(value) {
            hiddenEditView.inputType = value
            field = value
        }


    val text: String
        get() = hiddenEditView!!.text.toString().substring(0, length)

    init {
        init()
        initTypedArray(attrs)
    }

    private fun initTypedArray(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PasswordView)
        length = typedArray.getInt(R.styleable.PasswordView_length, 4)
        inputType = typedArray.getInt(
                R.styleable.PasswordView_android_inputType, InputType.TYPE_CLASS_NUMBER
        )
        typedArray.recycle()
    }

    private fun onTextChanged(text: String) {
        when {
            text.length > length -> {
                count.onNext(length)
                return
            }
            text.length == length -> count.onNext(length)
            else -> count.onNext(text.length)
        }
        val characters = text.toCharArray()
        var i = 0
        for (character in characters) {
            viewList[i].character = (character)
            i++
        }
        while (i < length) {
            viewList[i].clearCharacter()
            i++
        }
    }


    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_password_view, this)
        hiddenEditView.onTextChanged {
            onTextChanged(it)
        }
    }

    private fun createPasswordViews(length: Int) {
        passwordView.removeAllViews()
        viewList.clear()
        for (i in 0 until length) {
            val characterView = PasswordCharacterView(context)
            viewList.add(characterView)

            passwordView.addView(characterView)

            characterView.changeLayoutParams {
                it as LinearLayout.LayoutParams
                it.width = 0
                it.weight = 1f
            }.invalidate()
        }
        passwordView.invalidate()
        invalidate()
    }

    fun clear() {
        hiddenEditView.setText("")
    }
}
