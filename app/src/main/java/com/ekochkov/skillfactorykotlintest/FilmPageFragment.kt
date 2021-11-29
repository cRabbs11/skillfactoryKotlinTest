package com.ekochkov.skillfactorykotlintest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ekochkov.skillfactorykotlintest.databinding.FragmentFilmPageBinding


class FilmPageFragment : Fragment() {

    companion object {
        const val FILM_OBJECT = "film"
    }

    lateinit var binding: FragmentFilmPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilmPageBinding.inflate(inflater, container, false)

        val film = arguments?.get(FILM_OBJECT) as Film

        binding.fab.setOnClickListener {

        }

        binding.includeContent.text.text = film.descr
        binding.toolbar.title = film.title
        binding.image.setImageResource(film.poster)

        return binding.root
    }
}