package com.minutiello.thegallery.maingallery

import android.graphics.Rect
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.minutiello.thegallery.R
import com.minutiello.thegallery.databinding.GalleryFragmentBinding

class GalleryFragment(factoryProducer: ViewModelProvider.Factory? = null) : Fragment() {

    private var _binding: GalleryFragmentBinding? = null
    private val binding get() = _binding!!

    private val redditAdapter = RedditImagesAdapter()

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
        setHasOptionsMenu(true)
        _binding = GalleryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel.imagesLiveData.observe(viewLifecycleOwner, { galleryUIModel ->
            bindUIModel(galleryUIModel)
        })
    }

    private fun bindUIModel(galleryUIModel: GalleryUIModel) {
        galleryUIModel.run {
            binding.searchProgress.visibility = if (searching) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.emptySearchHint.visibility = if (query.isNullOrEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.emptySearchResult.visibility =
                if (!query.isNullOrEmpty() && images.isEmpty() && !searching) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            redditAdapter.dataSet = images
            redditAdapter.notifyDataSetChanged()
        }
    }

    private fun setupRecyclerView() {
        val gridManager: RecyclerView.LayoutManager = GridAutoFitLayoutManager(
            requireContext(), resources.getDimension(R.dimen.grid_item_size).toInt()
        )
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridManager
            adapter = redditAdapter
            val spacing = resources.getDimensionPixelSize(R.dimen.grid_item_spacing)
            setPadding(spacing, spacing, spacing, spacing)
            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.set(spacing, spacing, spacing, spacing)
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.gallery_fragment_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val searchViewMenuItem = menu.findItem(R.id.search)
        val searchView = searchViewMenuItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_query_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.getImages(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.getImages(newText)
                return true
            }
        })
    }
}