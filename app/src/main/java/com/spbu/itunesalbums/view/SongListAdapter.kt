package com.spbu.itunesalbums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spbu.itunesalbums.R
import com.spbu.itunesalbums.model.Song
import kotlinx.android.synthetic.main.song_view.view.*

class SongListAdapter(
    private val songList: ArrayList<Song>
) : RecyclerView.Adapter<SongListAdapter.SongViewHolder>() {


    class SongViewHolder(private val songView: View) : RecyclerView.ViewHolder(songView) {
        fun bind(song: Song, position: Int) {
            songView.trackNumberTextView.text = "${position + 1}."
            songView.trackNameTextView.text = song.trackName
            songView.trackDurationTextView.text = convertMillisToMinutes(song.trackTimeMillis)
        }

        // 185000 millis -> 185 sec -> 3 min 5 sec -> 3:05
        private fun convertMillisToMinutes(millis: Int): String {
            val minutes = millis / (60000)
            val seconds = millis / 1000 - minutes * 60
            val secondsAsString = if (seconds < 10) "0$seconds" else seconds.toString()
            return "${minutes}:$secondsAsString"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val songView = LayoutInflater.from(parent.context).inflate(
            R.layout.song_view,
            parent,
            false
        )
        return SongViewHolder(songView)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songList[position], position)
    }

    override fun getItemCount() = songList.size
}