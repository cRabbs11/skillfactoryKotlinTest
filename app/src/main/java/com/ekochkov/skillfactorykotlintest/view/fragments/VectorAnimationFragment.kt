package com.ekochkov.skillfactorykotlintest.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.ekochkov.skillfactorykotlintest.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VectorAnimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VectorAnimationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vector_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img = view.findViewById<ImageView>(R.id.img)

        val btn1 = view.findViewById<Button>(R.id.btn_sample_1)
        val btn2 = view.findViewById<Button>(R.id.btn_sample_2)

        btn1.setOnClickListener {
            val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.anim_vector_sample)
            img.setImageDrawable(animatedVectorDrawable)
            animatedVectorDrawable?.start()
        }

        btn2.setOnClickListener {
            val animatedVectorDrawable = AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.anim_trim_vector_sample)
            img.setImageDrawable(animatedVectorDrawable)
            animatedVectorDrawable?.start()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment VectorAnimationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                VectorAnimationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}