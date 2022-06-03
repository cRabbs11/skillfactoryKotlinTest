package com.ekochkov.skillfactorykotlintest.utils

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationManagerCompat
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.recievers.NotificationReceiver
import com.ekochkov.skillfactorykotlintest.utils.NotificationConstants.NOTIFICATION_CHANNEL_ID
import com.ekochkov.skillfactorykotlintest.view.activities.MainActivity

object NotificationHelper {

    private const val SHOW_THIS_FILM = "посмотеть данное кино"

    fun showDelayFilmNotification(context : Context, film: Film, delayInMillis: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(film.title, null, context, NotificationReceiver()::class.java)

        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_FILM, film)
        intent.putExtra(Constants.BUNDLE_KEY_INTENT, bundle)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setExact(
            AlarmManager.RTC,
            System.currentTimeMillis() + delayInMillis,
            pendingIntent
        )
    }

    fun showFilmNotification(context : Context, film: Film) {
        val intent = Intent(context, MainActivity::class.java)
        val bundle = Bundle()
        bundle.putParcelable(Constants.BUNDLE_KEY_FILM, film)
        bundle.putInt(Constants.BUNDLE_KEY_INTENT, Constants.BUNDLE_INTENT_OPEN_FILM_FRAGMENT)
        intent.putExtra(Constants.BUNDLE_KEY, bundle)

        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(film.id, getNotification(context, film, SHOW_THIS_FILM, pendingIntent))
    }

    private fun getNotification(context : Context, film: Film, contentText: String, pendingIntent: PendingIntent): Notification {
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.Q) {
            val notification = Notification.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(film.title)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(androidx.transition.R.drawable.notification_icon_background)
                .build()
            return notification
        } else {
            val notification = Notification.Builder(context)
                .setContentTitle(film.title)
                .setContentText(contentText)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(androidx.transition.R.drawable.notification_icon_background)
                .build()
            return notification
        }
    }
}