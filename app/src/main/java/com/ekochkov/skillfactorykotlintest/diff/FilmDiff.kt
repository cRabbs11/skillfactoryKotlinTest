package com.ekochkov.skillfactorykotlintest.diff

import androidx.recyclerview.widget.DiffUtil
import com.ekochkov.skillfactorykotlintest.data.entity.Film

class FilmDiff(private val oldList: List<Film>, private val newList: List<Film>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition] == newList[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return (oldItem.title == newItem.title &&
                oldItem.descr == newItem.descr &&
                oldItem.poster == newItem.poster)
    }
}