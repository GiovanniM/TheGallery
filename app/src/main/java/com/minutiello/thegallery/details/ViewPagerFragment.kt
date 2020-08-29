package com.minutiello.thegallery.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.minutiello.thegallery.databinding.ViewpagerFragmentBinding
import com.minutiello.thegallery.redditrepository.RedditImage

private const val ARG_IMAGES = "ARG_IMAGES"
private const val ARG_SELECTED_IMAGE = "ARG_SELECTED_IMAGE"

class ViewPagerFragment : Fragment() {

    private var _binding: ViewpagerFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(images: List<RedditImage>, selectedImage: RedditImage): Fragment {
            return ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_IMAGES, ArrayList<RedditImage>(images))
                    putSerializable(ARG_SELECTED_IMAGE, selectedImage)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ViewpagerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val images = arguments?.getSerializable(ARG_IMAGES) as ArrayList<RedditImage>
        val selectedImage = arguments?.getSerializable(ARG_SELECTED_IMAGE) as RedditImage
        val adapter = RedditPagerAdapter(childFragmentManager, images)
        binding.viewPager.adapter = adapter
        binding.viewPager.currentItem = images.indexOf(selectedImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

private class RedditPagerAdapter(fm: FragmentManager, var dataSet: List<RedditImage> = emptyList()) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return dataSet.size
    }

    override fun getItem(position: Int): Fragment =
        DetailsPageFragment.newInstance(dataSet[position])
}