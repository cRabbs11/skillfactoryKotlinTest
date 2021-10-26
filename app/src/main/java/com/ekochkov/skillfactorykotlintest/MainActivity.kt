package com.ekochkov.skillfactorykotlintest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ekochkov.skillfactorykotlintest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        println("test git")

        binding.topAppBar.setNavigationOnClickListener {
            showToast("onMenuClick")
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    showToast("onSettingsClick")
                    true
                }
                R.id.search -> {
                    showToast("onSearchClick")
                    true
                }
                else -> false
            }
        }


        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.compile -> {
                    showToast("подборки")
                    true
                }
                R.id.fav -> {
                    showToast("избранное")
                    true
                }
                R.id.later -> {
                    showToast("позже")
                    true
                }
                else -> false
            }
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}