package com.dbnaza.shufflesongs.network.interactors

import com.dbnaza.shufflesongs.TrackMapper
import com.dbnaza.shufflesongs.network.ArtistsWithSongsListViewState
import com.dbnaza.shufflesongs.network.api.TracksApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

class TracksInteractor constructor(
    private val tracksApi: TracksApi,
    private val trackMapper: TrackMapper
) {
    suspend fun getArtistsWithSongs(ids: String):
            ArtistsWithSongsListViewState = withContext(IO) {
        try {
            val response = tracksApi.getTracks(ids).execute()
            val artists = trackMapper.toArtistList(response.body())
            if (artists.isNotEmpty()) {
                ArtistsWithSongsListViewState.SuccessState(artists)
            } else {
                ArtistsWithSongsListViewState.EmptyState
            }

        } catch (exception: Exception) {
            ArtistsWithSongsListViewState.ErrorState(exception)
        }
    }
}