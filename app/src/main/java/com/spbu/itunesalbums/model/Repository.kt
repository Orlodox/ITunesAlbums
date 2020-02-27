package com.spbu.itunesalbums.model

interface Repository {
    suspend fun getAlbumsByTerm(term: String): ArrayList<Album>
    suspend fun getRandomAlbums(): ArrayList<Album>
    suspend fun getSongsByAlbumId(id: Int): ArrayList<Song>
}