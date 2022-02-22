package com.ekochkov.skillfactorykotlintest.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.skillfactorykotlintest.*
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.databinding.FragmentFavoritesBinding
import com.ekochkov.skillfactorykotlintest.decoration.OffsetFilmItemDecoration
import com.ekochkov.skillfactorykotlintest.diff.FilmDiff
import com.ekochkov.skillfactorykotlintest.domain.Film
import com.ekochkov.skillfactorykotlintest.utils.AnimationHelper
import com.ekochkov.skillfactorykotlintest.view.activities.MainActivity
import com.ekochkov.skillfactorykotlintest.viewmodel.FavoritesFragmentViewModel


class FavoritesFragment : Fragment() {

    private lateinit var  binding: FragmentFavoritesBinding
    private lateinit var filmAdapter: FilmListAdapter

    private val viewModel by lazy {
        ViewModelProvider(this).get(FavoritesFragmentViewModel::class.java)
    }

    private var filmsDBInFav = listOf<Film>()
        set(value) {
            if (field==value) return
            field=value
            updateRecyclerView(filmsDBInFav)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmListLiveData.observe(viewLifecycleOwner, Observer {
            filmsDBInFav = getFilmListInFav(it)
        })

        AnimationHelper.performFragmentCircularRevealAnimation(view, requireActivity(), 3)

        filmAdapter = FilmListAdapter(object : FilmListAdapter.OnItemClickListener {
            override fun onClick(film: Film) {
                (activity as MainActivity).launchFilmPageFragment(film)
            }
        })

        val parallaxPosterDecorator = OffsetFilmItemDecoration()

        binding.recyclerView.apply {
            itemAnimator = ItemFilmAnimator(requireContext())
            adapter = filmAdapter
            addItemDecoration(parallaxPosterDecorator)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    Log.d("BMTH", "dx: $dx dy: $dy")
                    if (recyclerView.childCount > 3) {
                        val view = recyclerView.getChildAt(2)
                    }
                }
            })
        }

        //val newFilmList = FilmRepository().filmList
        updateRecyclerView(filmsDBInFav)
    }

    private fun updateRecyclerView(films: List<Film>) {
        val diff = FilmDiff(filmAdapter.filmList, films)
        val diffResult = DiffUtil.calculateDiff(diff)
        filmAdapter.filmList.clear()
        filmAdapter.filmList.addAll(films)
        diffResult.dispatchUpdatesTo(filmAdapter)
    }

    private fun getFilmListInFav(films: List<Film>) : List<Film> {
        val list = arrayListOf<Film>()
        films.forEach {
            if (it.isInFav) {
                list.add(it)
            }
        }
        return list
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {

            }
    }
}