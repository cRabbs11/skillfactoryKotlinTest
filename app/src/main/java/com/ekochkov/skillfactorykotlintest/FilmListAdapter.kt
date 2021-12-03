package com.ekochkov.skillfactorykotlintest

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class FilmListAdapter(val fragment: HomeFragment, private val onClickListener: OnItemClickListener<Film>) : RecyclerView.Adapter<FilmItemHolder>() {

    var filmList = ArrayList<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.film_item_layout, parent, false)
        return FilmItemHolder(view)
    }

    override fun onBindViewHolder(holder: FilmItemHolder, position: Int) {
        val film = filmList[position]
        holder.titleText.text = film.title
        holder.descrText.text = film.descr
        holder.poster.setImageResource(film.poster)
        holder.poster.transitionName = "transition${position}"
        holder.itemView.setOnClickListener {
            onClickListener.onClick(film, holder.poster)
        }

        if (holder.poster.transitionName==fragment.sharedTransitionPosition) {
            fragment.startPostponedEnterTransition()
            Log.d("BMTH", "onBindHolder")
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    interface OnItemClickListener<T> {
        fun onClick(film: T, sharedView: View)
    }
}

class FilmItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    val titleText: TextView
    val descrText: TextView
    val poster: ImageView

    init {
        titleText = itemView.findViewById(R.id.title_text)
        descrText = itemView.findViewById(R.id.descr_text)
        poster = itemView.findViewById(R.id.image_poster)
    }
}

