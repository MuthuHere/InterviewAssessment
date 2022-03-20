package com.muthu.sph.util

import android.content.Context
import android.preference.PreferenceManager

class SharedPrefHelper(context: Context) {

    private val PREF_API_KEY = "API_KEY";

    private val pref = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String?) {
        pref.edit().putString(PREF_API_KEY, key).apply()
    }


    fun getApiKey() =
        pref.getString(PREF_API_KEY, null)

}