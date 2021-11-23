package com.ekochkov.skillfactorykotlintest

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.ekochkov.skillfactorykotlintest.databinding.FragmentDialogBinding

class SampleDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnN.setOnClickListener {
            Toast.makeText(view.context, "No", Toast.LENGTH_SHORT).show()
        }

        binding.btnOther.setOnClickListener {
            Toast.makeText(view.context, "Other", Toast.LENGTH_SHORT).show()
        }

        binding.btnY.setOnClickListener {
            Toast.makeText(view.context, "Yes", Toast.LENGTH_SHORT).show()

        }
    }

}