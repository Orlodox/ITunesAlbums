package com.spbu.itunesalbums.view

import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.model.Song

interface AlbumInfoView {
    fun showAlbumInfo(album: Album, songList: ArrayList<Song>)
    fun closeAlbum()
    fun toggleLoadingSongsMessage(isShow: Boolean)
}