package com.dbnaza.shufflesongs.network.api

import com.dbnaza.shufflesongs.model.Artist
import com.dbnaza.shufflesongs.model.Track
import com.dbnaza.shufflesongs.network.api.model.TrackListResponse
import com.dbnaza.shufflesongs.network.api.model.TrackResponse

class TrackMapper {

    fun toArtistList(trackListResponse: TrackListResponse?): List<Artist> {
        val artists = mutableListOf<Artist>()

        val results = trackListResponse?.results

        results?.filter {
            it.wrapperType == WrapperType.ARTIST.wrapper
        }?.map {
            artists.add(toArtist(it))
        }

        artists.map { artist ->
            results?.filter {
                it.wrapperType == WrapperType.TRACK.wrapper
                        && it.artistId == artist.id
            }?.map {
                artist.tracks.add(toTrack(it))
            }
        }

        return artists
    }

    fun toArtist(trackResponse: TrackResponse): Artist {
        return Artist(
            id = trackResponse.id,
            name = trackResponse.artistName,
            genre = trackResponse.primaryGenreName,
            artistType = trackResponse.artistType!!
        )
    }

    fun toTrack(trackResponse: TrackResponse): Track {
        return Track(
            id = trackResponse.id,
            name = trackResponse.trackName!!,
            artistName = trackResponse.artistName,
            artistId = trackResponse.artistId!!,
            genre = trackResponse.primaryGenreName,
            cover = trackResponse.artworkUrl!!
        )
    }

    enum class WrapperType(val wrapper: String) {
        TRACK("track"),
        ARTIST("artist")
    }
}