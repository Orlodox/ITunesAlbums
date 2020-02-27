package com.spbu.itunesalbums.view

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbu.itunesalbums.R
import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.model.Song
import com.spbu.itunesalbums.presenter.AlbumInfoPresenter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_album_info.*


class AlbumInfoFragment : Fragment(), AlbumInfoView {

    private lateinit var presenter: AlbumInfoPresenter
    private lateinit var songListAdapter: SongListAdapter
    private lateinit var songListLayoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        songListLayoutManager = LinearLayoutManager(this.context)
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // back to search results
        backButton.setOnClickListener {
            presenter.onBackButtonClick()
        }
        presenter.viewIsReady()
    }

    fun attachPresenter(presenter: AlbumInfoPresenter) {
        this.presenter = presenter
        presenter.attachView(this)
    }

    override fun showAlbumInfo(album: Album, songList: ArrayList<Song>) {
        Picasso.get().load(makeNewSizeImageUrl(album.artworkUrl100))
            .placeholder(R.drawable.image_placeholder).into(bigAlbumArtworkImageView)

        songListAdapter = SongListAdapter(songList)
        songRecyclerView.adapter = songListAdapter
        songRecyclerView.layoutManager = songListLayoutManager

        // prepare for insert links (to apple site) in TextViews of albumName and artistName
        albumNameInfoTextView.movementMethod = LinkMovementMethod.getInstance()
        artistNameInfoTextView.movementMethod = LinkMovementMethod.getInstance()
        albumNameInfoTextView.text = makeLink(album.collectionName, album.collectionViewUrl)
        artistNameInfoTextView.text = makeLink(album.artistName, album.artistViewUrl)

        genreInfoTextView.text = album.primaryGenreName
        // releaseDate example: "2018-08-24T07:00:00Z" ,
        // we take only a year (4 first chars)
        // result example: USA · 2018
        countryAndRelease.text = "${album.country}  ·  ${album.releaseDate.subSequence(0, 4)}"
        // result example: 8.5 USD
        priceInfoTextView.text = "${album.collectionPrice} ${album.currency}"
        copyright.text = album.copyright
    }

    private fun makeNewSizeImageUrl(defaultUrl: String, newSize: Int = 1500): String {
        // default URL example: https://is2-ssl.mzstatic.com/image/thumb/Music/v4/6f/df/25/6fdf2519-8ed5-55f1-9934-13a4501793fc/source/60x60bb.jpg
        // replace last URL part "60 x 60" with "newSize x newSize"
        val newUrlEnding = "${newSize}x${newSize}bb.jpg"
        return defaultUrl.replaceAfterLast('/', newUrlEnding)
    }

    // "a" - HTML tag of link
    private fun makeLink(text: String, link: String) =
        Html.fromHtml("<a href='${link}'>${text}</a>")

    override fun closeAlbum() {
        (this.context as MainActivity).closeAlbum()
    }

    override fun toggleLoadingSongsMessage(isShow: Boolean) {
        if (isShow) {
            loadingSongsTextView.visibility = View.VISIBLE
            songRecyclerView.visibility = View.GONE
        } else {
            loadingSongsTextView.visibility = View.GONE
            songRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
