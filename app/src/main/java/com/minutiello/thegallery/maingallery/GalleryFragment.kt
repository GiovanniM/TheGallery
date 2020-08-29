package com.minutiello.thegallery.maingallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.minutiello.thegallery.R

class GalleryFragment(factoryProducer: ViewModelProvider.Factory? = null) : Fragment() {

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
        return inflater.inflate(R.layout.gallery_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val textView = view.findViewById<TextView>(R.id.textview)
        viewModel.imagesLiveData.observe(viewLifecycleOwner, Observer { images ->
            textView.text = images.joinToString()
        })
        viewModel.getImages("cat")
    }
}