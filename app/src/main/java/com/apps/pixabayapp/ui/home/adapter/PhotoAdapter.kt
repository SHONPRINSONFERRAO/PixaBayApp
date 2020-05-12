package com.apps.pixabayapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.model.ListOfPhotos
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.photo_layout.view.*

class PhotoAdapter(private val photos: ArrayList<ListOfPhotos>,
                   private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<PhotoAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(photo: ListOfPhotos, clickListener: OnItemClickListener) {
            itemView.apply {
                Glide.with(photoItem.context)
                    .load(photo.previewURL)
                    .placeholder(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(photoItem)
            }

            itemView.setOnClickListener {
                clickListener.onItemClick(photo)
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
        holder.bind(photo = photos[position], clickListener = itemClickListener)
    }

    fun addUsers(photo: ArrayList<ListOfPhotos>) {
        this.photos.apply {
            addAll(photo)
        }

    }

    fun clearList() {
        this.photos.apply {
            photos.clear()
        }

    }

    interface OnItemClickListener {
        fun onItemClick(item: ListOfPhotos)
    }

}