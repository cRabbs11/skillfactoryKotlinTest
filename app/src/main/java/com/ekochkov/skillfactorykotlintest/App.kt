package com.ekochkov.skillfactorykotlintest

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.ekochkov.base_rest.DaggerRemoteComponent
import com.ekochkov.skillfactorykotlintest.di.AppComponent
import com.ekochkov.skillfactorykotlintest.di.DaggerAppComponent
import com.ekochkov.skillfactorykotlintest.di.modules.DatabaseModule
import com.ekochkov.skillfactorykotlintest.di.modules.DomainModule
import com.ekochkov.skillfactorykotlintest.utils.NotificationConstants.NOTIFICATION_CHANNEL_DESCRIPTION
import com.ekochkov.skillfactorykotlintest.utils.NotificationConstants.NOTIFICATION_CHANNEL_ID
import com.ekochkov.skillfactorykotlintest.utils.NotificationConstants.NOTIFICATION_CHANNEL_NAME


class App: Application() {
    lateinit var dagger: AppComponent
    var isWatchFilmShown = false

    override fun onCreate() {
        super.onCreate()
        instance = this
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .remoteProvider(remoteProvider)
            .build()

        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, importance)
            channel.description = NOTIFICATION_CHANNEL_DESCRIPTION
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}