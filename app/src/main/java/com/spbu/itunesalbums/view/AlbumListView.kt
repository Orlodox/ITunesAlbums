package com.spbu.itunesalbums.view

import com.spbu.itunesalbums.model.Album

interface AlbumListView {
    fun showAlbumList(albumList: ArrayList<Album>)
    fun getSearchTerm(): String
    fun openAlbum(album: Album)
    fun toggleLoadingMessage(isShow: Boolean)
    fun showNoResults()
}