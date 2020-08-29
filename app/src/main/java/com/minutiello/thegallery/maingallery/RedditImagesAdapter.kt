package com.minutiello.thegallery.maingallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.RedditViewBinding
import com.minutiello.thegallery.datamodel.RedditImage

class RedditImageViewHolder(
    val binding: RedditViewBinding,
) : RecyclerView.ViewHolder(binding.root)

class RedditImagesAdapter(var dataSet: List<RedditImage> = emptyList()) :
    RecyclerView.Adapter<RedditImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditImageViewHolder {
        return RedditImageViewHolder(RedditViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RedditImageViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(dataSet[position].thumbUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .into(holder.binding.imageView)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}