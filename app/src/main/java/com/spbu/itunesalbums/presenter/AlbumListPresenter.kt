package com.spbu.itunesalbums.presenter

import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.model.ITunesRepository
import com.spbu.itunesalbums.model.Repository
import com.spbu.itunesalbums.view.AlbumListFragment
import com.spbu.itunesalbums.view.AlbumListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AlbumListPresenter : OnAlbumClickListener {

    private var fragment: AlbumListView? = null

    // singleton kotlin object
    private val repository: Repository = ITunesRepository
    private var currentAlbumList: ArrayList<Album> = arrayListOf()

    fun onSearchButtonClick() {
        val term = fragment?.getSearchTerm() ?: return
        fragment?.toggleLoadingMessage(true)
        CoroutineScope(IO).launch {
            currentAlbumList =
                ArrayList(repository
                    .getAlbumsByTerm(term)
                    // did not sort by default
                    .sortedBy { it.collectionName })
            withContext(Main) {
                if (currentAlbumList.isEmpty())
                    fragment?.showNoResults()
                else {
                    fragment?.showAlbumList(currentAlbumList)
                    fragment?.toggleLoadingMessage(false)
                }
            }
        }
    }

    fun attachView(fragment: AlbumListFragment) {
        this.fragment = fragment
    }

    // called from view during destruction to avoid memory leaks
    fun detachView() {
        fragment = null
    }

    // called from view when it is fully created
    fun viewIsReady() {
        fragment?.toggleLoadingMessage(true)
        // if the first run or search results were not
        // then load random albums (then can do it according to user preferences)
        if (currentAlbumList.isEmpty()) {
            CoroutineScope(IO).launch {
                currentAlbumList =
                    ArrayList(repository.getRandomAlbums().sortedBy { it.collectionName })
                withContext(Main) {
                    fragment?.showAlbumList(currentAlbumList)
                }
            }
        }
        // else show what was previously
        else fragment?.showAlbumList(currentAlbumList)
        fragment?.toggleLoadingMessage(false)
    }

    override fun onAlbumClicked(album: Album) {
        fragment?.openAlbum(album)
    }
}

// for convenient binding in adapter
interface OnAlbumClickListener {
    fun onAlbumClicked(album: Album)
}