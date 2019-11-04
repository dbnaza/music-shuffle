package com.dbnaza.shufflesongs.ui.viewmodel

import androidx.annotation.VisibleForTesting
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
import com.dbnaza.shufflesongs.util.copyArtist
import com.dbnaza.shufflesongs.util.shuffleArtist
import com.dbnaza.shufflesongs.util.shuffleTrack
import kotlinx.coroutines.FlowPreview


@FlowPreview
class TracksViewModel(private val tracksInteractor: TracksInteractor) : ViewModel() {

    val tracksLiveData = MutableLiveData<List<Track>>()
    val stateLiveData = MutableLiveData<ArtistsWithSongsListViewState>()
    var artists: List<Artist> = mutableListOf()
    var hasLoadFinished = false

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

    fun shuffleSongs() {
        tracksLiveData.postValue(getArtistsTracksShuffledList(artists))
    }

    fun getArtistsTracksShuffledList(artists: List<Artist>) : MutableList<Track> {
        var artistsCopy = artists.copyArtist()
        val shuffled = mutableListOf<Track>()
        var lastArtistInFirstPosition: Artist? = null

        artistsCopy.map {
            it.tracks = it.tracks.shuffleTrack()
        }

        for (i in 0 until MAX_SONGS_LIMIT) {
            artistsCopy = artistsCopy.shuffleArtist()
            for (j in artistsCopy.indices) {
                val currentArtist = artistsCopy[j]
                if (j == 0 && currentArtist.id == lastArtistInFirstPosition?.id) {
                    changeFirstArtist(artistsCopy)
                }
                shuffled.add(artistsCopy[j].tracks[i])
                lastArtistInFirstPosition = currentArtist
            }
        }

        return shuffled
    }

    private fun changeFirstArtist(copyArtists: MutableList<Artist>) {
        val firstArtist = copyArtists[0]
        copyArtists[0] = copyArtists[1]
        copyArtists[1] = firstArtist
    }

}