package com.muthu.sph.di.viewmodel

import com.muthu.sph.di.api.ApiModule
import com.muthu.sph.di.api.AppModule
import com.muthu.sph.di.api.PrefsModule
import com.muthu.sph.viewmodel.SphListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, PrefsModule::class, AppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: SphListViewModel)

}