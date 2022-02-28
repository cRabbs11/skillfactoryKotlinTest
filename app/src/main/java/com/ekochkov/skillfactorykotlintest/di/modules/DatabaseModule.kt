package com.ekochkov.skillfactorykotlintest.di.modules

import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFilmRepository(): FilmRepository = FilmRepository()
}