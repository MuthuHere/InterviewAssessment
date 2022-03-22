package com.muthu.sph.di.api

import android.app.Application
import com.muthu.sph.util.SharedPrefHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class PrefsModule {

    @Provides
    @Singleton
    fun providerSharedPrefs(app: Application): SharedPrefHelper {
        return SharedPrefHelper(app)
    }
}