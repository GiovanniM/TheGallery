package com.minutiello.thegallery.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.DetailsPageFragmentBinding
import com.minutiello.thegallery.redditrepository.AppDatabaseFactory
import com.minutiello.thegallery.redditrepository.ImagesRepositoryFactory
import com.minutiello.thegallery.redditrepository.RedditImage
import com.minutiello.thegallery.redditrepository.RedditServiceFactory
import java.util.*

private const val ARG_IMAGE_ID = "ARG_IMAGE_ID"

class DetailsPageFragment(factoryProducer: ViewModelProvider.Factory? = null) : Fragment() {

    private var _binding: DetailsPageFragmentBinding? = null
    private val binding get() = _binding!!

    private var showingInfo = true

    companion object {
        fun newInstance(id: String): Fragment {
            return DetailsPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_ID, id)
                }
            }
        }
    }

    private val viewModel: DetailsViewModel by viewModels {
        factoryProducer ?: ViewModelFactory(
            DetailsUseCaseImpl(
                ImagesRepositoryFactory().getImagesRepository(
                    AppDatabaseFactory.getInstance(requireContext()).redditImageDao(),
                    RedditServiceFactory().getRedditService()
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsPageFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun getRedditImageId(): String {
        return arguments?.getString(ARG_IMAGE_ID) ?: ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.imageLiveData
            .observe(viewLifecycleOwner, { redditImage: RedditImage ->
                loadImage(redditImage)
                loadInfo(redditImage)
                initClickListener()
                activity?.invalidateOptionsMenu()
            })
        viewModel.getImage(getRedditImageId())
    }

    private fun loadImage(redditImage: RedditImage) {
        Glide.with(this)
            .load(redditImage.fullUrl)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .into(binding.fullImageView)
    }

    private fun loadInfo(redditImage: RedditImage) {
        redditImage.run {
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
            binding.favouriteButton.setImageResource(
                if (favourite == true) {
                    R.drawable.ic_favorite
                } else {
                    R.drawable.ic_not_favorite
                }
            )
        }
    }

    private fun initClickListener() {
        binding.fullImageView.setOnClickListener {
            if (showingInfo) {
                binding.topContainer.animate()
                    .translationY(-binding.topContainer.measuredHeight.toFloat() * 2)
                binding.imageDateLikesTextView.animate()
                    .translationY(binding.imageDateLikesTextView.measuredHeight.toFloat() * 2)
            } else {
                binding.topContainer.animate().translationY(0f)
                binding.imageDateLikesTextView.animate().translationY(0f)
            }
            showingInfo = !showingInfo
        }
        binding.favouriteButton.setOnClickListener {
            viewModel.changeFavourite(getRedditImageId())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}