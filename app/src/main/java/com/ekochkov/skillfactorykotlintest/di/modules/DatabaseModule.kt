package com.ekochkov.skillfactorykotlintest.di.modules

import android.content.Context
import androidx.room.Room
import com.ekochkov.skillfactorykotlintest.data.AppDataBase
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.data.dao.FilmDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideFilmDao(context: Context): FilmDao {
        return Room.databaseBuilder(
            context,
            AppDataBase::class.java,
            AppDataBase.CASHED_FILMS_TABLE_NAME
        ).build().filmDao()
    }

    @Singleton
    @Provides
    fun provideFilmRepository(filmDao: FilmDao): FilmRepository = FilmRepository(filmDao)
}