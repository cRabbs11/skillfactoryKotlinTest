package com.ekochkov.skillfactorykotlintest

import android.os.Bundle
import android.util.Log

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.*
import com.ekochkov.skillfactorykotlintest.databinding.FragmentHomeBinding
import com.ekochkov.skillfactorykotlintest.decoration.OffsetFilmItemDecoration
import com.ekochkov.skillfactorykotlintest.diff.FilmDiff

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
    private lateinit var  binding: FragmentHomeBinding
    private var isFragmentCreate = false

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
        AnimationHelper.performFragmentCircularRevealAnimation(view, requireActivity(), 1)
        if (isFragmentCreate) {
            isFragmentCreate = false
        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null) searchFilmByTitle(newText)
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
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (binding.recyclerView.childCount > 3) {
                        val view = binding.recyclerView.getChildAt(2)
                    }
                }
            })
        }
        updateRecyclerView(FilmRepository.getFilmList())
    }

    private fun searchFilmByTitle(query: String) {
        val list = FilmRepository.getFilmByTitleQuery(query)
        updateRecyclerView(list)
    }

    private fun showToast(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun updateRecyclerView(films: ArrayList<Film>) {
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