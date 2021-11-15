package com.ekochkov.skillfactorykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilmListAdapter : RecyclerView.Adapter<FilmItemHolder>() {

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
    }

    override fun getItemCount(): Int {
        return filmList.size
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

