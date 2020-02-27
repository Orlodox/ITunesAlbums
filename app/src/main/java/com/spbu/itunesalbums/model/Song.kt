package com.spbu.itunesalbums.model

data class Song(
    val wrapperType: String = "",// "track"
    val kind: String = "",// "song"
    val artistId: Int = 0,
    val collectionId: Int = 0,
    val trackId: Int = 0,
    val artistName: String = "",
    val collectionName: String = "",
    val trackName: String = "",
    val collectionCensoredName: String = "",
    val trackCensoredName: String = "",
    val artistViewUrl: String = "",
    val collectionViewUrl: String = "",
    val trackViewUrl: String = "",
    val previewUrl: String = "",
    val artworkUrl30: String = "",
    val artworkUrl60: String = "",
    val artworkUrl100: String = "",
    val collectionPrice: Double = 0.0,
    val trackPrice: Double = 0.0,
    val releaseDate: String = "",
    val collectionExplicitness: String = "",
    val trackExplicitness: String = "",
    val discCount: Int = 0,
    val discNumber: String = "",
    val trackCount: Int = 0,
    val trackNumber: Int = 0,
    val trackTimeMillis: Int = 0,
    val country: String = "",
    val currency: String = "",
    val primaryGenreName: String = "",
    val isStreamable: Boolean = false
)