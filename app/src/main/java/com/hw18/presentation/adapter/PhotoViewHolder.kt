package com.hw18.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hw18.data.Photo
import com.hw18.databinding.ItemPhotoBinding

class PhotoViewHolder(private val binding: ItemPhotoBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(photo: Photo) {
        Glide.with(binding.photo.context)
            .load(photo.imgUri)
            .centerCrop()
            .into(binding.photo)

        val fileName = photo.imgUri
            .substringAfterLast("/")
            .substringBeforeLast('.')
        binding.textPhoto.text = fileName
    }
}
