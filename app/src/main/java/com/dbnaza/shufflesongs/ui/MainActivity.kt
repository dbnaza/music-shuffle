package com.dbnaza.shufflesongs.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dbnaza.shufflesongs.R
import com.dbnaza.shufflesongs.network.ArtistsWithSongsListViewState
import com.dbnaza.shufflesongs.ui.viewmodel.TracksViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.FlowPreview
import org.koin.androidx.viewmodel.ext.android.viewModel


@FlowPreview
class MainActivity : AppCompatActivity() {

    private val viewModel: TracksViewModel by viewModel()

    private val adapter: TracksAdapter by lazy {
        TracksAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        configureList()
        configureClick()

        viewModel.getTracks()
        addObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_shuffle, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_shuffle -> {
                if (viewModel.hasLoadFinished) {
                    viewModel.shuffleSongs()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    private fun configureClick() {
        errorView.setOnClickListener {
            viewModel.getTracks()
        }
    }

    private fun addObservers() {
        viewModel.tracksLiveData.observe(this, Observer { songs ->
            adapter.submitList(songs)
        })

        viewModel.stateLiveData.observe(this, Observer { state ->
            val displayedChild = when (state) {
                is ArtistsWithSongsListViewState.LoadingState -> ListState.LOADING.child
                is ArtistsWithSongsListViewState.SuccessState -> ListState.LIST.child
                is ArtistsWithSongsListViewState.ErrorState -> ListState.ERROR.child
                ArtistsWithSongsListViewState.EmptyState -> ListState.EMPTY.child
            }

            viewFlipperTracks.displayedChild = displayedChild
        })
    }

    private fun configureList() {
        val dividerItemDecoration = DividerItemDecoration(
            this,
            LinearLayoutManager.VERTICAL
        )
        val drawable = ContextCompat.getDrawable(this, R.drawable.divider)
        drawable?.let {
            dividerItemDecoration.setDrawable(it)
            trackList.addItemDecoration(dividerItemDecoration)
        }
        trackList.adapter = adapter
    }

    enum class ListState(val child: Int) {
        LOADING(0),
        LIST(1),
        EMPTY(2),
        ERROR(3)
    }
}
