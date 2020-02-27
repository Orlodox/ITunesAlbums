package com.spbu.itunesalbums.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.spbu.itunesalbums.R
import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.presenter.AlbumInfoPresenter
import com.spbu.itunesalbums.presenter.AlbumListPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    private lateinit var albumListFragment: AlbumListFragment
    private lateinit var albumInfoFragment: AlbumInfoFragment

    private lateinit var albumListPresenter: AlbumListPresenter
    private lateinit var albumInfoPresenter: AlbumInfoPresenter

    // fragment displayed on screen
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMainComponents()
        openAlbumListFragment()
    }

    private fun initMainComponents() {
        albumListFragment = AlbumListFragment()
        albumInfoFragment = AlbumInfoFragment()

        // it initialize here one time so that when manipulating open and closing a albumInfoFragment,
        // the current album search results and scroll position in albumListFragment are not lost
        albumListPresenter = AlbumListPresenter()
    }

    private fun openAlbumListFragment() {
        currentFragment = albumListFragment

        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, albumListFragment)
            .commit()

        albumListFragment.attachPresenter(albumListPresenter)
    }

    fun openAlbumInfoFragment(openedAlbum: Album) {
        currentFragment = albumInfoFragment
        // init here, because every time we open a new album
        // and you don’t need to save anything from the previous opening
        albumInfoPresenter = AlbumInfoPresenter(openedAlbum)

        supportFragmentManager
            .beginTransaction()
            .replace(fragmentContainer.id, albumInfoFragment)
            .commit()

        albumInfoFragment.attachPresenter(albumInfoPresenter)
    }

    fun closeAlbum() {
        openAlbumListFragment()
    }

    // for intuitive navigation between fragments, return to the search results
    override fun onBackPressed() {
        if (currentFragment is AlbumInfoFragment) closeAlbum()
        else super.onBackPressed()
    }

}
