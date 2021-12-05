package com.ekochkov.skillfactorykotlintest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
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

    private val filmList = ArrayList<Film>()
    private lateinit var  binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FilmListAdapter(object : FilmListAdapter.OnItemClickListener {
            override fun onClick(film: Film) {
                (activity as MainActivity).launchFilmPageFragment(film)
            }
        })

        val parallaxPosterDecorator = OffsetFilmItemDecoration()

        binding.recyclerView.itemAnimator = ItemFilmAnimator(requireContext())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(parallaxPosterDecorator)
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("BMTH", "dx: $dx dy: $dy")
                if (recyclerView.childCount > 3) {
                    val view = recyclerView.getChildAt(2)
                }
            }
        })

        adapter.filmList = filmList
        val filmRepository = FilmRepository
        val newFilmList = filmRepository.getFilmList()
        val diff = FilmDiff(adapter.filmList, newFilmList)
        val diffResult = DiffUtil.calculateDiff(diff)
        adapter.filmList.clear()
        adapter.filmList.addAll(newFilmList)
        diffResult.dispatchUpdatesTo(adapter)

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

        binding.topAppBar.setNavigationOnClickListener {
            showToast(resources.getString(R.string.main_menu))
        }
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
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}