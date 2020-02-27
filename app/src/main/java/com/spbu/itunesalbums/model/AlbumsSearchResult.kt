package com.spbu.itunesalbums.model

data class AlbumsSearchResult(
    val resultCount: Int = 0,
    val results: ArrayList<Album> = arrayListOf()
)