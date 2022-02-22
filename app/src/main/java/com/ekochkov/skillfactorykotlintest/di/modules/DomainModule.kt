package com.ekochkov.skillfactorykotlintest.di.modules

import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideInteractor(repository: FilmRepository, tmdbRetrofitService: TmdbAPI): Interactor = Interactor(repository = repository, tmdbRetrofitService = tmdbRetrofitService)
}