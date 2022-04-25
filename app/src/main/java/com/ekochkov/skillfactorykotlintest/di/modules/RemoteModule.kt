package com.ekochkov.skillfactorykotlintest.di.modules

import com.ekochkov.skillfactorykotlintest.BuildConfig
import com.ekochkov.skillfactorykotlintest.utils.TmdbAPI
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {

    private val CALL_TIMEOUT_MILLI_30 = 30L

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
        .readTimeout(CALL_TIMEOUT_MILLI_30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            if(BuildConfig.DEBUG) {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        })
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(TmdbApiConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideRetrofiteService(retrofit: Retrofit): TmdbAPI = retrofit.create(TmdbAPI::class.java)
}