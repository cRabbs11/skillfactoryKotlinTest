package com.ekochkov.skillfactorykotlintest

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import com.ekochkov.skillfactorykotlintest.databinding.PosterBinding

class PosterView(context: Context, @Nullable attributeSet: AttributeSet): ConstraintLayout(context, attributeSet) {

    private val imageView: ImageView
    private val textView: TextView

    init {
        val binding = PosterBinding.inflate(LayoutInflater.from(context), this)
        imageView = binding.imagePoster
        textView = binding.textPoster

        imageView.setOnClickListener {
            Toast.makeText(context, textView.text, Toast.LENGTH_SHORT).show()
        }
    }
}