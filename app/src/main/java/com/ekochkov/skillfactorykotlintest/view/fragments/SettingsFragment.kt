package com.ekochkov.skillfactorykotlintest.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ekochkov.skillfactorykotlintest.R
import com.ekochkov.skillfactorykotlintest.databinding.FragmentSettingsBinding
import com.ekochkov.skillfactorykotlintest.utils.AnimationHelper
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants
import com.ekochkov.skillfactorykotlintest.viewmodel.SettingsFragmentViewModel

class SettingsFragment: Fragment() {

    lateinit var binding: FragmentSettingsBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(SettingsFragmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(view, requireActivity(), 5)

        viewModel.typeCategoryLifeData.observe(viewLifecycleOwner, {
            when(it) {
                FILM_LIST_TYPE_POPULAR -> {binding.categoryTypeGroup.check(R.id.popular_movies)}
                FILM_LIST_TYPE_NOW_PLAYING -> {binding.categoryTypeGroup.check(R.id.now_playing_movies)}
                FILM_LIST_TYPE_LATEST -> {binding.categoryTypeGroup.check(R.id.latest_movies)}
                FILM_LIST_TYPE_TOP_RATED -> {binding.categoryTypeGroup.check(R.id.top_rated_movies)}
                FILM_LIST_TYPE_UPCOMING -> {binding.categoryTypeGroup.check(R.id.upcoming_movies)}
            }
        })

        binding.categoryTypeGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.popular_movies -> {
                    viewModel.setTypeCategory(FILM_LIST_TYPE_POPULAR)}
                R.id.now_playing_movies -> {
                    viewModel.setTypeCategory(FILM_LIST_TYPE_NOW_PLAYING)}
                R.id.latest_movies -> {
                    viewModel.setTypeCategory(FILM_LIST_TYPE_LATEST) }
                R.id.top_rated_movies -> {
                    viewModel.setTypeCategory(FILM_LIST_TYPE_TOP_RATED)}
                R.id.upcoming_movies -> {
                    viewModel.setTypeCategory(FILM_LIST_TYPE_UPCOMING)}
            }
        }
    }

    companion object {
        private val FILM_LIST_TYPE_NOW_PLAYING = TmdbApiConstants.FILM_LIST_TYPE_NOW_PLAYING
        private val FILM_LIST_TYPE_LATEST = TmdbApiConstants.FILM_LIST_TYPE_LATEST
        private val FILM_LIST_TYPE_TOP_RATED = TmdbApiConstants.FILM_LIST_TYPE_TOP_RATED
        private val FILM_LIST_TYPE_UPCOMING = TmdbApiConstants.FILM_LIST_TYPE_UPCOMING
        private val FILM_LIST_TYPE_POPULAR = TmdbApiConstants.FILM_LIST_TYPE_POPULAR
    }
}