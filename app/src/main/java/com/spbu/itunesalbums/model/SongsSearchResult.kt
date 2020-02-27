package com.spbu.itunesalbums.model

data class SongsSearchResult(
    val resultCount: Int = 0,
    val results: ArrayList<Song> = arrayListOf()
)