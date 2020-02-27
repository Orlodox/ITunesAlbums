package com.spbu.itunesalbums.model

import android.util.Log
import retrofit2.HttpException
import kotlin.random.Random

object ITunesRepository : Repository {

    override suspend fun getAlbumsByTerm(term: String): ArrayList<Album> {

        var results = arrayListOf<Album>()
        try {
            results = NetworkService.getAPI().searchAlbumsByTerm(term).results
        } catch (e: HttpException) {
            Log.e("NetworkException", "HTTP exception!")
        } catch (e: Throwable) {
            Log.e("NetworkException", "Some exception!")
        }

        return results
    }

    // load albums by random character (then can do it according to user preferences)
    override suspend fun getRandomAlbums(): ArrayList<Album> {
        val lettersCount = 'z' - 'a' + 1
        val randomLetter = (Random.nextInt(lettersCount) + 'a'.toInt()).toChar().toString()
        return getAlbumsByTerm(randomLetter)
    }

    override suspend fun getSongsByAlbumId(id: Int): ArrayList<Song> {
        var results = arrayListOf<Song>()

        try {
            results = NetworkService.getAPI().searchSongsByCollectionId(id).results
        } catch (e: HttpException) {
            Log.e("NetworkException", "HTTP exception!")
        } catch (e: Throwable) {
            Log.e("NetworkException", "Some exception!")
        }
        // at the 0 position of array is the album object contains these songs
        // for convenience is was cast it to a song and will remove
        results.removeAt(0)
        return results
    }
}