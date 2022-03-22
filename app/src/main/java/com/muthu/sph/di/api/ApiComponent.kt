package com.muthu.sph.di.api

import com.muthu.sph.service.ApiService
import dagger.Component

/**
 * Dependency Injection
 * [ApiModule] is being injected in [ApiService]
 */
@Component(modules = [ApiModule::class,])
interface ApiComponent {
    fun inject(apiService: ApiService)
}