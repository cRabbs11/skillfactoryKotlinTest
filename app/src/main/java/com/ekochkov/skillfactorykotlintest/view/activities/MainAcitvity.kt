package com.ekochkov.skillfactorykotlintest.view.activities

import android.animation.Animator
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.ekochkov.skillfactorykotlintest.R
import com.ekochkov.skillfactorykotlintest.databinding.ActivityMainRecyclerViewBinding
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.view.fragments.*
import java.util.*

private const val TAG_HOME_FRAGMENT = "home_fragment"
private const val TAG_FILM_PAGE_FRAGMENT = "film_page_fragment"
private const val TAG_FAV_FRAGMENT = "fav_fragment"
private const val TAG_LATER_FRAGMENT = "later_fragment"
private const val TAG_COMPILE_FRAGMENT = "compile_fragment"
private const val TAG_SETTINGS_FRAGMENT = "settings_fragment"

private const val MESSAGE_BATTERY_LOW = "низкий уровень батареи"
private const val MESSAGE_POWER_CONNECTED = "батарея заряжается"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainRecyclerViewBinding
    private val receiver = ChargeReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runSplashScreen(1, object: Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                binding.lottieAnim.visibility = View.GONE
                launchHomeFragment()
            }
        })
        //binding.container!!.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        //binding.container!!.layoutTransition.setAnimator(LayoutTransition.APPEARING, AnimatorInflater.loadAnimator(this, R.animator.sample_animator))


        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    launchHomeFragment()
                    true
                }
                R.id.compile -> {
                    launchCompilesFragment()
                    true
                }
                R.id.fav -> {
                    launchFavoritesFragment()
                    true
                }
                R.id.later -> {
                    launchLaterFragment()
                    true
                }
                R.id.settings -> {
                    launchSettingsFragment()
                    true
                }
                else -> false
            }
        }

        val filter = IntentFilter(Intent.ACTION_BATTERY_LOW)
        filter.addAction(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(receiver, filter)

    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    private var backPressed = 0L
    companion object {
        const val TIME_INTERVAL = 2000
    }

    private fun runSplashScreen(repeatCount: Int, listener: Animator.AnimatorListener) {
        val lottieAnimationView: LottieAnimationView = binding.lottieAnim
        lottieAnimationView.repeatCount = repeatCount
        lottieAnimationView.playAnimation()
        lottieAnimationView.addAnimatorListener(listener)
    }

    override fun onBackPressed() {
        val lastFragmentIndex = supportFragmentManager.backStackEntryCount - 1
        if (lastFragmentIndex<0) {
            super.onBackPressed()
        } else {
            val lastFragmentTag = supportFragmentManager.getBackStackEntryAt(lastFragmentIndex).name
            if (lastFragmentIndex >= 1 && lastFragmentTag == TAG_FILM_PAGE_FRAGMENT) {
                super.onBackPressed()
            } else {
                runDoubleBackPressedToExit()
            }
        }
    }

    private fun runDoubleBackPressedToExit() {
        if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            super.onBackPressed()
            finish()
        } else {
            showToast(resources.getString(R.string.tap_again_to_exit))
        }
        backPressed = System.currentTimeMillis()
    }

    private fun getExistFragmentByTag(tag: String): Fragment? {
        return supportFragmentManager.findFragmentByTag(tag)
    }

    private fun launchFragment(fragment: Fragment, tag: String?) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, fragment, tag)
                .addToBackStack(tag)
                .commit()
    }

    fun launchHomeFragment() {
        val fragment = getExistFragmentByTag(TAG_HOME_FRAGMENT)
        launchFragment(fragment?: HomeFragment(), TAG_HOME_FRAGMENT)
    }

    fun launchCompilesFragment() {
        val fragment = getExistFragmentByTag(TAG_COMPILE_FRAGMENT)
        launchFragment(fragment?: CompilesFragment(), TAG_COMPILE_FRAGMENT)
    }

    fun launchFavoritesFragment() {
        val fragment = getExistFragmentByTag(TAG_FAV_FRAGMENT)
        launchFragment(fragment?: FavoritesFragment(), TAG_FAV_FRAGMENT)
    }

    fun launchLaterFragment() {
        val fragment = getExistFragmentByTag(TAG_LATER_FRAGMENT)
        //Фрагмент с примерами векторной анимации
        //val fragment = VectorAnimationFragment()
        launchFragment(fragment?: LaterFragment(), TAG_LATER_FRAGMENT)
    }

    fun launchSettingsFragment() {
        val fragment = getExistFragmentByTag(TAG_SETTINGS_FRAGMENT)
        launchFragment(fragment?: SettingsFragment(), TAG_SETTINGS_FRAGMENT)
    }

    fun launchFilmPageFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable(FilmPageFragment.FILM_OBJECT, film)

        val fragment = FilmPageFragment()
        fragment.arguments = bundle

        launchFragment(fragment, TAG_FILM_PAGE_FRAGMENT)
    }

    fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
            val time = "$hour $minute"
            showToast(time)
        }
        TimePickerDialog(this, timePicker, currentHour, currentMinute, true).show()
    }

    fun showDateTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val date = "$year $month $dayOfMonth"

            val timePicker = TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                val time = "$hour $minute $date"
                showToast(time)
            }
            TimePickerDialog(this, timePicker, currentHour, currentMinute, true).show()
        }, currentYear, currentMonth, currentDay).show()
    }

    fun showAlertDialog() {
        AlertDialog.Builder(ContextThemeWrapper(this, R.style.MyDialog))
                .setTitle("На выход?")
                .setIcon(R.drawable.ic_baseline_settings_24)
                .setPositiveButton("Офкорс") {_,_ ->
                    finish()
                }
                .setNegativeButton("Ноуп") {_,_ ->

                }
                .setNeutralButton("Донт Ноу") {_,_ ->
                    showToast("Думай")
                }
                .show()
    }

    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            val time = "$year $month $dayOfMonth"
            showToast(time)
        }, currentYear, currentMonth, currentDay).show()

    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    inner class ChargeReceiver: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_BATTERY_LOW -> {
                    showToast(MESSAGE_BATTERY_LOW)
                }
                Intent.ACTION_POWER_CONNECTED -> {
                    showToast(MESSAGE_POWER_CONNECTED)
                }
            }
        }
    }
}