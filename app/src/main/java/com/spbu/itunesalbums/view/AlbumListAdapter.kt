package com.spbu.itunesalbums.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spbu.itunesalbums.R
import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.presenter.OnAlbumClickListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_view.view.*

class AlbumListAdapter(
    private val albumList: ArrayList<Album>,
    private val itemClickListener: OnAlbumClickListener
) : RecyclerView.Adapter<AlbumListAdapter.AlbumViewHolder>() {

    fun setData(newAlbumList: ArrayList<Album>) {
        albumList.clear()
        albumList.addAll(newAlbumList)
        notifyDataSetChanged()
    }


    class AlbumViewHolder(private val albumView: View) : RecyclerView.ViewHolder(albumView) {

        // interface is in presenter's file (and presenter implements it)
        fun bind(album: Album, clickListener: OnAlbumClickListener) {
            albumView.albumNameTextView.text = album.collectionName
            albumView.artistNameTextView.text = album.artistName
            Picasso.get().load(album.artworkUrl100).placeholder(R.drawable.image_placeholder)
                .into(albumView.albumArtworkImageView)

            // open second fragment with this album info
            albumView.setOnClickListener {
                clickListener.onAlbumClicked(album)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val albumView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.album_view, parent,
                false
            )

        return AlbumViewHolder(albumView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(albumList[position], itemClickListener)
    }


    override fun getItemCount() = albumList.size
}