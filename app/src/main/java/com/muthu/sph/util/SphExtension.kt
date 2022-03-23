package com.muthu.sph.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
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


fun Context.checkInternetAccess(): Boolean {

    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
// if the android version is equal to M
// or greater we need to use the
// NetworkCapabilities to check what type of
// network has the internet connection
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        // Returns a Network object corresponding to
        // the currently active default data network.
        val network = connectivityManager.activeNetwork ?: return false
        // Representation of the capabilities of an active network.
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            // Indicates this network uses a Wi-Fi transport,
            // or WiFi has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            // Indicates this network uses a Cellular transport. or
            // Cellular has network connectivity
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    } else {
        // if the android version is below M
        @Suppress("DEPRECATION") val networkInfo =
            connectivityManager.activeNetworkInfo ?: return false
        @Suppress("DEPRECATION")
        return networkInfo.isConnected
    }
}
