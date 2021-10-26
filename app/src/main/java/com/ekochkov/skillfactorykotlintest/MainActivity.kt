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
            showToast("onNavigationClick")
        }

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.fav -> {
                    showToast("onFavClick")
                    true
                }
                R.id.search -> {
                    showToast("onSearchClick")
                    true
                }
                R.id.more -> {
                    showToast("onMoreClick")
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