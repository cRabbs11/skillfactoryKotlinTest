package com.ekochkov.skillfactorykotlintest.view.fragments

import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.ekochkov.skillfactorykotlintest.FilmListAdapter
import com.ekochkov.skillfactorykotlintest.ItemFilmAnimator
import com.ekochkov.skillfactorykotlintest.data.FilmRepository
import com.ekochkov.skillfactorykotlintest.databinding.FragmentHomeBinding
import com.ekochkov.skillfactorykotlintest.decoration.OffsetFilmItemDecoration
import com.ekochkov.skillfactorykotlintest.diff.FilmDiff
import com.ekochkov.skillfactorykotlintest.domain.Film
import com.ekochkov.skillfactorykotlintest.utils.AnimationHelper
import com.ekochkov.skillfactorykotlintest.view.activities.MainActivity
import com.ekochkov.skillfactorykotlintest.viewmodel.HomeFragmentViewModel
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    private lateinit var filmAdapter: FilmListAdapter
    private lateinit var binding: FragmentHomeBinding
    private var isFragmentCreate = false

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeFragmentViewModel::class.java)
    }

    private var filmsDB = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            updateRecyclerView(filmsDB)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFragmentCreate = true
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.filmListLiveData.observe(viewLifecycleOwner, {
            filmsDB = it
        })

        AnimationHelper.performFragmentCircularRevealAnimation(view, requireActivity(), 1)
        if (isFragmentCreate) {
            isFragmentCreate = false
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) searchFilmByTitle(newText)
                return true
            }

        })
        binding.searchView.setOnClickListener {
            binding.searchView.isIconified = false
        }

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
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    val layoutManager = (binding.recyclerView.layoutManager as LinearLayoutManager)
                    val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                    viewModel.getLastVisibleFilmInList(lastVisiblePosition)
                }
            })
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refreshFilms()
            binding.swipeRefresh.isRefreshing = false
        }
        updateRecyclerView(filmsDB)
    }

    private fun searchFilmByTitle(query: String) {
        val result = filmsDB.filter {
            it.title.toLowerCase(Locale.getDefault())
                    .contains(query.toLowerCase(Locale.getDefault()))
        }
        updateRecyclerView(result)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(films: List<Film>) {
        val diff = FilmDiff(filmAdapter.filmList, films)
        val diffResult = DiffUtil.calculateDiff(diff)
        filmAdapter.filmList.clear()
        filmAdapter.filmList.addAll(films)
        diffResult.dispatchUpdatesTo(filmAdapter)
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
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}