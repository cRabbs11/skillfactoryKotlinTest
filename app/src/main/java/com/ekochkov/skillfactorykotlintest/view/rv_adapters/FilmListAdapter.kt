package com.ekochkov.skillfactorykotlintest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ekochkov.skillfactorykotlintest.databinding.FilmItemLayoutBinding
import com.ekochkov.skillfactorykotlintest.data.entity.Film
import com.ekochkov.skillfactorykotlintest.utils.TmdbApiConstants

class FilmListAdapter(private val onClickListener: OnItemClickListener) : RecyclerView.Adapter<FilmItemHolder>() {

    var filmList = ArrayList<Film>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmItemHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.film_item_layout, parent, false)
        return FilmItemHolder(view)
    }

    override fun onBindViewHolder(holder: FilmItemHolder, position: Int) {
        val film = filmList[position]
        holder.binding.titleText.text = film.title
        holder.binding.descrText.text = film.descr
        holder.binding.filmRating.setProgress(film.rating)

        Glide.with(holder.itemView)
            .load(TmdbApiConstants.IMAGES_URL + "w342" + film.poster)
            .centerCrop()
            .into(holder.binding.imagePoster)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(film)
        }
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    interface OnItemClickListener {
        fun onClick(film: Film)
    }
}

class FilmItemHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val binding = FilmItemLayoutBinding.bind(itemView)
}

