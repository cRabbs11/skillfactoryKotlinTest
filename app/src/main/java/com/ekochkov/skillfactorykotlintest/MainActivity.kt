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

        binding.topAppBar.setNavigationOnClickListener {
            showToast(resources.getString(R.string.main_menu))
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    showToast(resources.getString(R.string.settings))
                    true
                }
                R.id.search -> {
                    showToast(resources.getString(R.string.search))
                    true
                }
                else -> false
            }
        }


        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.compile -> {
                    showToast(resources.getString(R.string.compilations))
                    true
                }
                R.id.fav -> {
                    showToast(resources.getString(R.string.favorites))
                    true
                }
                R.id.later -> {
                    showToast(resources.getString(R.string.later))
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