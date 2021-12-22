package com.ekochkov.skillfactorykotlintest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ekochkov.skillfactorykotlintest.databinding.ActivityMainRecyclerViewBinding
import java.util.*

private const val TAG_HOME_FRAGMENT = "home_fragment"
private const val TAG_FILM_PAGE_FRAGMENT = "film_page_fragment"
private const val TAG_FAV_FRAGMENT = "home_fragment"
private const val TAG_LATER_FRAGMENT = "later_fragment"
private const val TAG_COMPILE_FRAGMENT = "compile_fragment"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launchHomeFragment()

        //binding.container!!.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        //binding.container!!.layoutTransition.setAnimator(LayoutTransition.APPEARING, AnimatorInflater.loadAnimator(this, R.animator.sample_animator))


        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    launchHomeFragment()
                    true
                }
                R.id.compile -> {
                    showToast(resources.getString(R.string.compilations))
                    //if (binding.cardView5.alpha==1F) {
                    //    binding.cardView5.animate()
                    //            .setDuration(150)
                    //            .alpha(0.5F)
                    //} else {
                    //    binding.cardView5.animate()
                    //            .setDuration(150)
                    //            .alpha(1F)
                    //}
                    true
                }
                R.id.fav -> {
                    showToast(resources.getString(R.string.favorites))
                    launchFavoritesFragment()
                    //if (binding.container!!.childCount>0) {
                    //    binding.container!!.removeViewAt(binding.container!!.childCount-1)
                    //}
                    true
                }
                R.id.later -> {
                    showToast(resources.getString(R.string.later))
                    //ObjectAnimator.ofFloat(binding.imageView3, View.SCALE_Y, 0.5F, 1F).start()
                    true
                }
                else -> false
            }
        }
    }

    private var backPressed = 0L
    companion object {
        const val TIME_INTERVAL = 2000
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            if (backPressed + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                finish()
            } else {
                showToast(resources.getString(R.string.tap_again_to_exit))
            }
            backPressed = System.currentTimeMillis()
        } else {
            super.onBackPressed()
        }
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

    fun launchFavoritesFragment() {
        val fragment = getExistFragmentByTag(TAG_FAV_FRAGMENT)
        launchFragment(fragment?: FavoritesFragment(), TAG_FAV_FRAGMENT)
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
}