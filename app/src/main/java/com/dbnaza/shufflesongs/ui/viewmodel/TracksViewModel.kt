package com.dbnaza.shufflesongs.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dbnaza.shufflesongs.SelectedArtists
import com.dbnaza.shufflesongs.model.Artist
import com.dbnaza.shufflesongs.model.Track
import com.dbnaza.shufflesongs.network.ArtistsWithSongsListViewState
import com.dbnaza.shufflesongs.network.interactors.TracksInteractor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers.Main
import com.dbnaza.shufflesongs.application.ShuffleSongsApplication.Companion.MAX_SONGS_LIMIT
import kotlinx.coroutines.FlowPreview


class TracksViewModel(private val tracksInteractor: TracksInteractor) : ViewModel() {

    val tracksLiveData = MutableLiveData<List<Track>>()
    val stateLiveData = MutableLiveData<ArtistsWithSongsListViewState>()
    var artists: List<Artist> = mutableListOf()
    var hasLoadFinished = false

    @FlowPreview
    fun getTracks() {
        stateLiveData.postValue(ArtistsWithSongsListViewState.LoadingState)

        CoroutineScope(Main).launch {
            val result = tracksInteractor.getArtistsWithSongs(SelectedArtists.artists)
            if (result is ArtistsWithSongsListViewState.SuccessState) {
                hasLoadFinished = true
                artists = result.artists
                shuffleSongs()
            }

            stateLiveData.postValue(result)
        }
    }

    @FlowPreview
    fun shuffleSongs() {
        val artistsCopy = ArrayList(artists.map { it.copy() })
        val shuffled = mutableListOf<Track>()
        var lastArtistInFirstPosition: Artist? = null

        artistsCopy.forEach {
            it.tracks.shuffle()
        }

        for (i in 0 until MAX_SONGS_LIMIT) {
            artistsCopy.shuffle()
            for (j in 0 until artistsCopy.size) {
                val currentArtist = artistsCopy[j]
                if (j == 0 && currentArtist.id == lastArtistInFirstPosition?.id) {
                    changeFirstArtist(artistsCopy)
                }
                shuffled.add(artistsCopy[j].tracks[i])
                lastArtistInFirstPosition = currentArtist
            }
        }
        tracksLiveData.postValue(shuffled)
    }

    private fun changeFirstArtist(copyArtists: MutableList<Artist>) {
        val firstArtist = copyArtists[0]
        copyArtists[0] = copyArtists[1]
        copyArtists[1] = firstArtist
    }

}