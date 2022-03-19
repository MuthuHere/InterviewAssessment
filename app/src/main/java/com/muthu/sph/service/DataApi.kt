package com.muthu.sph.service

import com.muthu.sph.model.ListDataModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface to register all the endpoints
 */
interface DataApi {

    @GET("datastore_search")
    fun getData(@Query("resource_id") id: String): Single<ListDataModel>

}