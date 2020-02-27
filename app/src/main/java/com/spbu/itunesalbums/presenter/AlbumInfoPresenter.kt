package com.spbu.itunesalbums.presenter

import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.model.ITunesRepository
import com.spbu.itunesalbums.model.Repository
import com.spbu.itunesalbums.model.Song
import com.spbu.itunesalbums.view.AlbumInfoView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumInfoPresenter(private val openedAlbum: Album) {

    private var fragment: AlbumInfoView? = null

    // singleton kotlin object
    private val repository: Repository = ITunesRepository
    private var songList: ArrayList<Song> = arrayListOf()

    // called from view after mounting it
    fun attachView(fragment: AlbumInfoView) {
        this.fragment = fragment
    }

    // called from view during destruction to avoid memory leaks
    fun detachView() {
        fragment = null
    }

    // called from view when it is fully created to fetch songs
    fun viewIsReady() {
        fragment?.toggleLoadingSongsMessage(true)
        CoroutineScope(IO).launch {
            songList = ArrayList(repository
                .getSongsByAlbumId(openedAlbum.collectionId)
                // by default they are already sorted like this, but what if the API changes?
                .sortedBy { it.trackNumber })
            withContext(Main) {
                fragment?.showAlbumInfo(openedAlbum, songList)
                fragment?.toggleLoadingSongsMessage(false)
            }
        }
    }

    // back to search results (close albumInfo and open albumList from activity)
    fun onBackButtonClick() {
        fragment?.closeAlbum()
    }

}