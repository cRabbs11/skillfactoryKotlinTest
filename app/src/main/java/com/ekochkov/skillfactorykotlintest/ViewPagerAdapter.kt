package com.ekochkov.skillfactorykotlintest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ViewPagerAdapter: RecyclerView.Adapter<ViewPagerHolder>() {

    private val items = mutableListOf<PagerItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_pager_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(list: List<PagerItem>) {
        items.clear()
        items.addAll(list)
    }

}