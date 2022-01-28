package com.ekochkov.skillfactorykotlintest.view.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import com.ekochkov.skillfactorykotlintest.databinding.ActivityFilmPageWithBottomSheetBinding
import com.ekochkov.skillfactorykotlintest.domain.Film
import com.google.android.material.bottomsheet.BottomSheetBehavior


class FilmPageWithBottomSheetActivity: AppCompatActivity() {

    lateinit var binding: ActivityFilmPageWithBottomSheetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilmPageWithBottomSheetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val film = intent.extras?.get("film") as Film

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)

        binding.fab.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }

        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {

            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                binding.tintBackground.alpha = slideOffset/1.3f
            }

        })

        binding.includeContent.text.text=film.descr
        binding.toolbar.title=film.title
        binding.image.setImageResource(film.poster)
    }
}