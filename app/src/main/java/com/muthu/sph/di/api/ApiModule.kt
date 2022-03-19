package com.muthu.sph.di.api

import com.muthu.sph.service.DataApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Class to provide and maintain retrofit client
 * [ApiModule]
 */
@Module
class ApiModule {
    private val baseUrl = "https://data.gov.sg/api/action/"

    @Provides
    fun provideDataApi(): DataApi {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DataApi::class.java)

    }
}