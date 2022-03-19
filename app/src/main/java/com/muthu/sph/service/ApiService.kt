package com.muthu.sph.service

import com.muthu.sph.di.api.DaggerApiComponent
import com.muthu.sph.model.ListDataModel
import io.reactivex.Single
import javax.inject.Inject

class ApiService {

    @Inject
    lateinit var api: DataApi

    init {
        DaggerApiComponent.create().inject(this)
    }


    fun getData(id:String): Single<ListDataModel> {
        return api.getData(id)
    }


}