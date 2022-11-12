package com.rahi.donut.util.extensions

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.os.Build

/**
 * Created by Rahi on 11/November/2022
 */

fun Context.isInternetAvailable(): Boolean {
    val manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities =  manager.getNetworkCapabilities(manager.activeNetwork)
        networkCapabilities != null
    } else {
        val activeNetwork = manager.activeNetworkInfo
        activeNetwork?.isConnectedOrConnecting == true && activeNetwork.isAvailable
    }
}