package com.muthu.sph.util

import android.content.Context
import com.google.gson.Gson
import com.muthu.sph.model.ListDataModel


class SharedPrefHelper(context: Context) {
    private val gson = Gson()
    private val prefData = "_PREF_DATA"

    private val pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)


    fun storeData(listDataModel: ListDataModel) {
        val json = gson.toJson(listDataModel)
        pref.edit().putString(prefData, json).apply()
    }

    fun retrieveData(): ListDataModel =
        gson.fromJson(pref.getString(prefData, null), ListDataModel::class.java)

}