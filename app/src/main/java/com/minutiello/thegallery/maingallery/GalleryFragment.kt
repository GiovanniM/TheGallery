package com.minutiello.thegallery.maingallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.minutiello.thegallery.databinding.GalleryFragmentBinding

class GalleryFragment(factoryProducer: ViewModelProvider.Factory? = null) : Fragment() {

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private val viewModel: GalleryViewModel by viewModels {
        factoryProducer ?: ViewModelFactory(GalleryUseCaseImpl())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = GalleryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gridManager: RecyclerView.LayoutManager = GridLayoutManager(context, 3)
        val redditAdapter = RedditImagesAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridManager
            adapter = redditAdapter
        }
        viewModel.imagesLiveData.observe(viewLifecycleOwner, Observer { images ->
            redditAdapter.dataSet = images
            redditAdapter.notifyDataSetChanged()
        })
        viewModel.getImages("cat")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}