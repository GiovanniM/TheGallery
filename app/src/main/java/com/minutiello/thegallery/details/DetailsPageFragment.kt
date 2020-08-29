package com.minutiello.thegallery.details

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.DetailsPageFragmentBinding
import com.minutiello.thegallery.redditrepository.RedditImage

private const val ARG_IMAGE = "ARG_IMAGE"

class DetailsPageFragment : Fragment() {

    private var _binding: DetailsPageFragmentBinding? = null
    private val binding get() = _binding!!

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
        Glide.with(this)
            .load(redditImage?.fullUrl)
            .centerInside()
            .transition(DrawableTransitionOptions.withCrossFade())
            .fallback(R.drawable.ic_error)
            .error(R.drawable.ic_error)
            .into(binding.fullImageView)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}