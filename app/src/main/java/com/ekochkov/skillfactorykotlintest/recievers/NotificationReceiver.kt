package com.ekochkov.skillfactorykotlintest.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.utils.Constants
import com.ekochkov.skillfactorykotlintest.utils.NotificationHelper

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(Constants.BUNDLE_KEY_INTENT)
        val film = bundle?.getParcelable(Constants.BUNDLE_KEY_FILM) as Film?
        if (film!=null) {
            NotificationHelper.showFilmNotification(context!!, film)
        }
    }
}