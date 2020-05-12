package com.apps.pixabayapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.model.ListOfPhotos
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.photo_layout.view.*

class PhotoAdapter(private val photos: ArrayList<ListOfPhotos>) : RecyclerView.Adapter<PhotoAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: ListOfPhotos) {
            itemView.apply {
                Glide.with(photoItem.context)
                    .load(photo.previewURL)
                    .into(photoItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.photo_layout, parent, false))

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(photo = photos[position])
    }

    fun addUsers(photo: ArrayList<ListOfPhotos>) {
        this.photos.apply {
            addAll(photo)
        }

    }
}