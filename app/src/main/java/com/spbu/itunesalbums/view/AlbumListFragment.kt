package com.spbu.itunesalbums.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.spbu.itunesalbums.R
import com.spbu.itunesalbums.model.Album
import com.spbu.itunesalbums.presenter.AlbumListPresenter
import kotlinx.android.synthetic.main.fragment_album_list.*


class AlbumListFragment : Fragment(), AlbumListView {

    private lateinit var albumListAdapter: AlbumListAdapter
    private lateinit var albumListLayoutManager: RecyclerView.LayoutManager
    private lateinit var presenter: AlbumListPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        albumListLayoutManager = LinearLayoutManager(this.context)
        albumListAdapter = AlbumListAdapter(arrayListOf(), presenter)

        return inflater.inflate(R.layout.fragment_album_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumListRecyclerView.adapter = albumListAdapter
        albumListRecyclerView.layoutManager = albumListLayoutManager

        searchButton.setOnClickListener {
            presenter.onSearchButtonClick()
        }
        presenter.viewIsReady()
    }

    override fun showAlbumList(albumList: ArrayList<Album>) {
        albumListAdapter.setData(albumList)
    }

    // user's search request in EditTextView
    override fun getSearchTerm() = searchEditText.text.toString()

    fun attachPresenter(presenter: AlbumListPresenter) {
        this.presenter = presenter
        presenter.attachView(this)
    }

    override fun openAlbum(album: Album) {
        (this.context as MainActivity).openAlbumInfoFragment(album)
    }

    override fun toggleLoadingMessage(isShow: Boolean) {
        if (isShow) {
            noResultsTextView.visibility = View.GONE
            albumListRecyclerView.visibility = View.GONE
            loadingTextView.visibility = View.VISIBLE
        } else {
            loadingTextView.visibility = View.GONE
            noResultsTextView.visibility = View.GONE
            albumListRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun showNoResults() {
        noResultsTextView.visibility = View.VISIBLE
        albumListRecyclerView.visibility = View.GONE
        loadingTextView.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }


}
