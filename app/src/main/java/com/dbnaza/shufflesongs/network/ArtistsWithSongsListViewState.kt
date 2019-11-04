package com.dbnaza.shufflesongs.network

import com.dbnaza.shufflesongs.model.Artist

sealed class ArtistsWithSongsListViewState {
    data class SuccessState(val artists: List<Artist>) : ArtistsWithSongsListViewState()
    data class ErrorState(val e: Exception) : ArtistsWithSongsListViewState()
    object EmptyState : ArtistsWithSongsListViewState()
    object LoadingState : ArtistsWithSongsListViewState()
}