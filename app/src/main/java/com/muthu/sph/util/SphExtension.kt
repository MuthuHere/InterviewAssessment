package com.muthu.sph.util

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this.context, message, duration).show()
}

fun Double.roundOff(): Double {
    val df = DecimalFormat("#.#####")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}


/**
 * An extension to convert unmodified double value to string
 * this help to bind the value in a view
 */
fun Double.toSphString(): String {
    return if (this.isNaN() || this.isInfinite()) this.toString() else BigDecimal(this.toString()).stripTrailingZeros()
        .toPlainString()
}