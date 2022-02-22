package com.ekochkov.skillfactorykotlintest.view.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ekochkov.skillfactorykotlintest.domain.PagerItem
import com.ekochkov.skillfactorykotlintest.databinding.ViewPagerItemBinding


class ViewPagerHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun onBind(item: PagerItem) {
        val binding = ViewPagerItemBinding.bind(itemView)
        binding.container.setBackgroundColor(item.color)
        binding.textView.text=item.text
    }
}