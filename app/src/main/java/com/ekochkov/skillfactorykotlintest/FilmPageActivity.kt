package com.ekochkov.skillfactorykotlintest

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import com.ekochkov.skillfactorykotlintest.databinding.ActivityFilmPageBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_film_page.view.*

class FilmPageActivity: AppCompatActivity() {

    lateinit var binding: ActivityFilmPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film = intent.extras?.get("film") as Film

        binding.fab.setOnClickListener {

        }

        binding.includeContent.text.text=film.descr
        binding.toolbar.title=film.title
        binding.toolbarLayout.image.setImageResource(film.poster)
    }
}