package com.minutiello.thegallery.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.DetailsPageFragmentBinding
import com.minutiello.thegallery.redditrepository.RedditImage
import java.util.*

private const val ARG_IMAGE = "ARG_IMAGE"

class DetailsPageFragment : Fragment() {

    private var _binding: DetailsPageFragmentBinding? = null
    private val binding get() = _binding!!

    private var showingInfo = true

    companion object {
        fun newInstance(redditImage: RedditImage): Fragment {
            return DetailsPageFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_IMAGE, redditImage)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        _binding = DetailsPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val redditImage: RedditImage? = arguments?.getSerializable(ARG_IMAGE) as RedditImage?
        loadImage(redditImage)
        loadInfo(redditImage)
        initClickListener()
    }

    private fun loadImage(redditImage: RedditImage?) {
        Glide.with(this)
            .load(redditImage?.fullUrl)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .into(binding.fullImageView)
    }

    private fun loadInfo(redditImage: RedditImage?) {
        redditImage?.run {
            val unknown = getString(R.string.unknown)
            val date: String = if (created != null) {
                Date(created * 1000).toString()
            } else {
                unknown
            }
            binding.imageTitleAuthorTextView.text =
                getString(R.string.title_and_author, title ?: unknown, author ?: unknown)
            binding.imageDateLikesTextView.text =
                getString(R.string.creation_and_likes, date, score ?: 0)
        }
    }

    private fun initClickListener() {
        binding.fullImageView.setOnClickListener {
            if (showingInfo) {
                binding.imageTitleAuthorTextView.animate()
                    .translationY(-binding.imageTitleAuthorTextView.measuredHeight.toFloat())
                binding.imageDateLikesTextView.animate()
                    .translationY(binding.imageDateLikesTextView.measuredHeight.toFloat())
            } else {
                binding.imageTitleAuthorTextView.animate().translationY(0f)
                binding.imageDateLikesTextView.animate().translationY(0f)
            }
            showingInfo = !showingInfo
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}