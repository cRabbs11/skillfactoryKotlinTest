package com.ekochkov.skillfactorykotlintest.di.modules

import android.content.Context
import com.ekochkov.skillfactorykotlintest.data.DatabaseHelper
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFilmRepository(databaseHelper: DatabaseHelper): FilmRepository = FilmRepository(databaseHelper)

    @Singleton
    @Provides
    fun provideDatabaseHelper(context: Context): DatabaseHelper = DatabaseHelper(context)
}