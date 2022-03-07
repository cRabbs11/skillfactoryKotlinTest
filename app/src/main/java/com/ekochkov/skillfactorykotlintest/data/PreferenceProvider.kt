package com.ekochkov.skillfactorykotlintest.data

import android.content.Context
import android.content.SharedPreferences
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants

const val KEY_DEFAULT_TYPE_CATEGORY = "default_category"

class PreferenceProvider(context: Context) {

    companion object {
        private val PREFERENCE_SETTINGS_FILENAME = "settings"

        private val KEY_FIRST_LAUNCH = "first_launch"
        private val DEFAULT_TYPE_CATEGORY = TmdbApiConstants.FILM_LIST_TYPE_POPULAR
    }

    private val appContext = context.applicationContext
    private val sharedPref = appContext.getSharedPreferences(PREFERENCE_SETTINGS_FILENAME, Context.MODE_PRIVATE)

    init {
        setPrefOnFirstAppLaunch()
    }

    fun saveDefaultTypeCategory(defaultTypeCategory: String) {
        val editor = sharedPref.edit()
        val put = editor.putString(KEY_DEFAULT_TYPE_CATEGORY, defaultTypeCategory)
        put.apply()
    }

    fun getDefaultTypeCategory(): String {
        return sharedPref.getString(KEY_DEFAULT_TYPE_CATEGORY, DEFAULT_TYPE_CATEGORY)?: DEFAULT_TYPE_CATEGORY
    }

    fun registerPrefListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPref.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterPrefListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun setPrefOnFirstAppLaunch() {
        if (sharedPref.getBoolean(KEY_FIRST_LAUNCH, true)) {
            sharedPref.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
            sharedPref.edit().putString(KEY_DEFAULT_TYPE_CATEGORY, DEFAULT_TYPE_CATEGORY).apply()
        }
    }

}