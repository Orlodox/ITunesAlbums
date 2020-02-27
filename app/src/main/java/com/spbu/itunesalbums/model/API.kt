package com.spbu.itunesalbums.model

import retrofit2.http.GET
import retrofit2.http.Query

interface API {

    @GET("/search")
    suspend fun searchAlbumsByTerm(
        @Query("term") term: String,
        // it's default limit
        @Query("limit") limit: Int = 50,
        @Query("attribute") attribute: String = "albumTerm",
        @Query("entity") entity: String = "album"
    ): AlbumsSearchResult


    @GET("/lookup")
    suspend fun searchSongsByCollectionId(
        @Query("id") id: Int,
        // the maximum limit is set, as some albums have more than 50 (the default number) tracks
        @Query("limit") limit: Int = 200,
        @Query("entity") entity: String = "song"
    ): SongsSearchResult

}