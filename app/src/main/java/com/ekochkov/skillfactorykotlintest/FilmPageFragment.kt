package com.ekochkov.skillfactorykotlintest

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.ekochkov.skillfactorykotlintest.databinding.FragmentFilmPageBinding


class FilmPageFragment : Fragment() {

    init {
        val slide = Slide(Gravity.END).addTarget(R.id.film_page_container)
        val fade = Fade().addTarget(R.id.film_page_container)
        val tranistion = TransitionSet().apply {
            addTransition(slide)
            addTransition(fade)
            duration = 250
        }
        enterTransition = tranistion
        returnTransition = tranistion
    }

    companion object {
        const val FILM_OBJECT = "film"
    }

    lateinit var binding: FragmentFilmPageBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFilmPageBinding.inflate(inflater, container, false)

        val film = arguments?.get(FILM_OBJECT) as Film
        setFavIcon(film)

        binding.fabFav.setOnClickListener {
            film.isInFav = !film.isInFav
            setFavIcon(film)
        }

        binding.fabShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT, "Look at this: ${film.title} \n\n ${film.descr}"
            )
            intent.type = "text/plain"

            startActivity(Intent.createChooser(intent, "Share:"))
        }

        binding.includeContent.text.text = film.descr
        binding.toolbar.title = film.title
        Glide.with(requireContext())
                .load(film.poster)
                .centerCrop()
                .into(binding.image)
        //binding.image.setImageResource(film.poster)

        return binding.root
    }

    private fun setFavIcon(film: Film) {
        if (film.isInFav) {
            binding.fabFav.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fabFav.setImageResource(R.drawable.ic_round_favorite_border_24)
        }
    }
}