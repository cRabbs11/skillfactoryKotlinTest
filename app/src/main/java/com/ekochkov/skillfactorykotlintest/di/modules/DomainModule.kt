package com.ekochkov.skillfactorykotlintest.di.modules

import android.content.Context
import com.ekochkov.base_rest.TmdbAPI
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.PreferenceProvider
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context) {

    @Provides
    fun provideContext(): Context = context

    @Provides
    @Singleton
    fun providePreferenceProvider(): PreferenceProvider = PreferenceProvider(context)

    @Singleton
    @Provides
    fun provideInteractor(repository: FilmRepository,
                          tmdbRetrofitService: TmdbAPI,
                          prefProvider: PreferenceProvider): Interactor =
        Interactor(repository = repository,
            tmdbRetrofitService = tmdbRetrofitService,
            preferenceProvider = prefProvider)
}