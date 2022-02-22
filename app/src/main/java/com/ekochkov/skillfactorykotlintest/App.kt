package com.ekochkov.skillfactorykotlintest

import android.app.Application
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.di.AppComponent
import com.ekochkov.skillfactorykotlintest.di.DaggerAppComponent
import com.ekochkov.skillfactorykotlintest.domain.Interactor
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class App: Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.create()
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}