package com.riteshakya.core.extension

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager


fun View.viewHeight(): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay

    val deviceWidth: Int


    val size = Point()
    display.getSize(size)
    deviceWidth = size.x


    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    measure(widthMeasureSpec, heightMeasureSpec)
    return measuredHeight
}

fun View.getViewWidth(): Int {
    val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = wm.defaultDisplay

    val deviceHeight: Int


    val size = Point()
    display.getSize(size)
    deviceHeight = size.y


    val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.AT_MOST)
    val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(
            deviceHeight, View.MeasureSpec.UNSPECIFIED
    )
    measure(widthMeasureSpec, heightMeasureSpec)
    return measuredWidth
}


fun View.setSelectivePadding(
        left: Int = paddingLeft, top: Int = paddingTop, right: Int = paddingRight,
        bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun View.changeLayoutParams(body: (layoutParams: ViewGroup.LayoutParams) -> Unit): View {
    val layoutParams = this.layoutParams
    body(layoutParams)
    this.layoutParams = layoutParams
    return this
}