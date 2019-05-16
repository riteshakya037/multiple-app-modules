package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_password_character.view.*


/**
 * author riteshakya037
 */
class PasswordCharacterView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var character: Char = ' '
        set(value) {
            field = value
            passwordCharView.text = value.toString()
            passwordCharView.visibility = VISIBLE
        }

    init {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_password_character, this)
    }


    fun clearCharacter() {
        passwordCharView.visibility = GONE
    }
}
