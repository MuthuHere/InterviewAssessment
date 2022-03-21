package com.muthu.sph

import com.muthu.sph.di.api.ApiModule
import com.muthu.sph.service.ApiService
import com.muthu.sph.service.DataApi

class ApiModuleTest(private val mockService: ApiService) : ApiModule() {

   override fun provideApiService(): ApiService {
      return mockService
   }
}