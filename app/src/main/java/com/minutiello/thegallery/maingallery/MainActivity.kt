package com.minutiello.thegallery.maingallery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minutiello.thegallery.R
import com.minutiello.thegallery.details.ViewPagerFragment
import com.minutiello.thegallery.redditrepository.RedditImage
import com.minutiello.thegallery.settings.SettingsFragment

private const val TRANSACTION_TAG = "TRANSACTION_TAG"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, GalleryFragment.newInstance())
            .commit()
    }

    fun loadViewPager(images: List<RedditImage>, selectedImage: RedditImage) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                ViewPagerFragment.newInstance(images, selectedImage)
            )
            .addToBackStack(TRANSACTION_TAG)
            .commit()
    }

    fun loadSettings() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, SettingsFragment())
            .addToBackStack(TRANSACTION_TAG)
            .commit()
    }
}