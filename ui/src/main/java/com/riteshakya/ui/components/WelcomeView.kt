package com.riteshakya.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.riteshakya.ui.R
import kotlinx.android.synthetic.main.custom_welcome_view.view.*


class WelcomeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var title: String = ""
        set(value) {
            field = value
            titleTxt.text = value
        }

    var subtitle: String = ""
        set(value) {
            field = value
            subtitleTxt.text = value
        }
    private var src: Int = 0
        set(value) {
            field = value
            profileImage.setImageResource(value)
        }
    var showBadge: Boolean = false
        set(value) {
            field = value
            profileBadge.visibility = if (value) View.VISIBLE else View.GONE
        }
    var badgeText: String = ""
        set(value) {
            field = value
            profileBadge.text = value
        }

    var badgeIsLight: Boolean = false
        set(value) {
            field = value
            val textAppearance =
                if (value) R.style.TextAppearance_Welcome_Badge_Light else R.style.TextAppearance_Welcome_Badge
            val background =
                if (value) R.drawable.background_welcome_badge_light else R.drawable.background_welcome_badge
            profileBadge.setTextAppearance(context, textAppearance)
            profileBadge.setBackgroundResource(background)
        }


    init {
        init()
        initTypedArray(attrs)
    }

    val image: ImageView by lazy { profileImage }

    private fun initTypedArray(attrs: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attrs, R.styleable.WelcomeView, 0, 0)
        title = ta.getString(R.styleable.WelcomeView_android_title) ?: ""
        subtitle = ta.getString(R.styleable.WelcomeView_android_subtitle) ?: ""
        src = ta.getResourceId(R.styleable.WelcomeView_android_src, 0)
        showBadge = ta.getBoolean(R.styleable.WelcomeView_showBadge, false)
        badgeText = ta.getString(R.styleable.WelcomeView_badgeText) ?: ""
        badgeIsLight = ta.getBoolean(
            R.styleable.WelcomeView_badgeIsLight,
            false
        )
        ta.recycle()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.custom_welcome_view, this)
    }

}