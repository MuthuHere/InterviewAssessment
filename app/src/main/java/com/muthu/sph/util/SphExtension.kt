package com.muthu.sph.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToInt

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}

fun Double.roundTo(): Double {
    val df = DecimalFormat("#.####")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}