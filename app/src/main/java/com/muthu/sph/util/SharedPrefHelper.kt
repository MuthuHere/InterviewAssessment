package com.muthu.sph.util

import android.content.Context
import com.google.gson.Gson
import com.muthu.sph.model.ListDataModel


/**
 * [SharedPrefHelper] is the class that helps to store and retrieve the data
 */
class SharedPrefHelper(context: Context) {
    private val gson = Gson()
    private val prefData = "_PREF_DATA"

    private val pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)


    /**
     * Retrieve the [ListDataModel]
     */
    fun storeData(listDataModel: ListDataModel) {
        val json = gson.toJson(listDataModel)
        pref.edit().putString(prefData, json).apply()
    }

    /**
     * store the [ListDataModel]
     */
    fun retrieveData(): ListDataModel? {
        val data = pref.getString(prefData, null) ?: return null
        return gson.fromJson(data, ListDataModel::class.java)
    }
}