package com.spbu.itunesalbums.model

data class Album(
    val wrapperType: String = "",// "collection",
    val collectionType: String = "",// "Album",
    val artistId: Int = 0,
    val collectionId: Int = 0,
    val amgArtistId: Int = 0,
    val artistName: String = "",
    val collectionName: String = "",
    val collectionCensoredName: String = "",
    val artistViewUrl: String = "",
    val collectionViewUrl: String = "",
    val artworkUrl60: String = "",
    val artworkUrl100: String = "",
    val collectionPrice: Double = 0.0,
    val collectionExplicitness: String = "",
    val trackCount: Int = 0,
    val copyright: String = "",
    val country: String = "",
    val currency: String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = ""
)