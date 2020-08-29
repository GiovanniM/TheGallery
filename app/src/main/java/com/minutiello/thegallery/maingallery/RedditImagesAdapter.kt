package com.minutiello.thegallery.maingallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.RedditViewBinding
import com.minutiello.thegallery.redditrepository.RedditImage

class RedditImageViewHolder(
    val binding: RedditViewBinding,
) : RecyclerView.ViewHolder(binding.root)

class RedditImagesAdapter(
    var dataSet: List<RedditImage> = emptyList(),
    private val listener: ((List<RedditImage>, RedditImage) -> Unit)? = null
) :
    RecyclerView.Adapter<RedditImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditImageViewHolder {
        return RedditImageViewHolder(RedditViewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RedditImageViewHolder, position: Int) {
        val redditImage = dataSet[position]
        Glide.with(holder.itemView)
            .load(redditImage.thumbUrl)
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .into(holder.binding.imageView)
        holder.itemView.setOnClickListener { listener?.invoke(dataSet, redditImage) }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}