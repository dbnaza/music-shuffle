package com.dbnaza.shufflesongs

import com.dbnaza.shufflesongs.network.api.TrackMapper
import com.dbnaza.shufflesongs.network.api.model.TrackListResponse
import com.dbnaza.shufflesongs.network.api.model.TrackResponse
import com.dbnaza.shufflesongs.testutils.StringGenerator.getRandomString
import kotlinx.coroutines.FlowPreview
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

@FlowPreview
class TracksMapperTest {

    val mapper = TrackMapper()

    private val ARTIST_ID = Random.nextLong()
    private val ARTIST_WRAPPER_TYPE = "artist"
    private val ARTIST_TYPE = getRandomString()
    private val ARTIST_NAME = getRandomString()
    private val GENRE = getRandomString()
    private val TRACK_WRAPPER_TYPE = "track"
    private val TRACK_ID = Random.nextLong()
    private val TRACK_EXPLICITNESS = getRandomString()
    private val TRACK_CENSORED_NAME = getRandomString()
    private val TRACK_NAME = getRandomString()
    private val COLLECTION_ID = Random.nextLong()
    private val TRACK_COUNTRY = getRandomString()
    private val TRACK_ARTWORK_URL = getRandomString()
    private val TRACK_RELEASE_DATE = getRandomString()
    private val TRACK_COLLECTION_NAME = getRandomString()
    private val TRACK_TIME_MILLIS = Random.nextLong()

    @Test
    fun onTrackResponseWithArtistWrapperTypePassed_mapsToArtist() {
        val trackResponse = TrackResponse(
            id = ARTIST_ID,
            wrapperType = ARTIST_WRAPPER_TYPE,
            artistType = ARTIST_TYPE,
            primaryGenreName = GENRE,
            artistName = ARTIST_NAME
        )

        val mappedArtist = mapper.toArtist(trackResponse)

        assertEquals(ARTIST_TYPE, mappedArtist.artistType)
        assertEquals(GENRE, mappedArtist.genre)
        assertEquals(ARTIST_ID, mappedArtist.id)
        assertEquals(ARTIST_NAME, mappedArtist.name)
    }

    @Test
    fun onTrackResponseWithTrackWrapperTypePassed_mapsToTrack() {
        val trackResponse = TrackResponse(
            id = TRACK_ID,
            wrapperType = TRACK_WRAPPER_TYPE,
            primaryGenreName = GENRE,
            artistName = ARTIST_NAME,
            artistId = ARTIST_ID,
            trackExplicitness = TRACK_EXPLICITNESS,
            trackCensoredName = TRACK_CENSORED_NAME,
            collectionId = COLLECTION_ID,
            country = TRACK_COUNTRY,
            artworkUrl = TRACK_ARTWORK_URL,
            releaseDate = TRACK_RELEASE_DATE,
            collectionName = TRACK_COLLECTION_NAME,
            trackTimeMillis = TRACK_TIME_MILLIS,
            trackName = TRACK_NAME
        )

        val mappedTrack = mapper.toTrack(trackResponse)

        assertEquals(ARTIST_NAME, mappedTrack.artistName)
        assertEquals(GENRE, mappedTrack.genre)
        assertEquals(ARTIST_ID, mappedTrack.artistId)
        assertEquals(TRACK_ID, mappedTrack.id)
        assertEquals(TRACK_NAME, mappedTrack.name)
        assertEquals(TRACK_ARTWORK_URL, mappedTrack.cover)
    }

    @Test
    fun onTrackResponseWithArtistAndTrackWrapperTypePassed_mapsToArtistListWithTracks() {
        val artistTrackResponse = TrackResponse(
            id = ARTIST_ID,
            wrapperType = ARTIST_WRAPPER_TYPE,
            artistType = ARTIST_TYPE,
            primaryGenreName = GENRE,
            artistName = ARTIST_NAME
        )

        val trackResponse = TrackResponse(
            id = TRACK_ID,
            wrapperType = TRACK_WRAPPER_TYPE,
            primaryGenreName = GENRE,
            artistName = ARTIST_NAME,
            artistId = ARTIST_ID,
            trackExplicitness = TRACK_EXPLICITNESS,
            trackCensoredName = TRACK_CENSORED_NAME,
            collectionId = COLLECTION_ID,
            country = TRACK_COUNTRY,
            artworkUrl = TRACK_ARTWORK_URL,
            releaseDate = TRACK_RELEASE_DATE,
            collectionName = TRACK_COLLECTION_NAME,
            trackTimeMillis = TRACK_TIME_MILLIS,
            trackName = TRACK_NAME
        )

        val trackListResponse = TrackListResponse(
            results = listOf(artistTrackResponse, trackResponse),
            resultCount = 2
        )

        val mappedArtist = mapper.toArtistList(trackListResponse).first()
        val mappedTrack = mappedArtist.tracks.first()

        assertEquals(ARTIST_TYPE, mappedArtist.artistType)
        assertEquals(GENRE, mappedArtist.genre)
        assertEquals(ARTIST_ID, mappedArtist.id)
        assertEquals(ARTIST_NAME, mappedArtist.name)
        assertEquals(ARTIST_NAME, mappedTrack.artistName)
        assertEquals(GENRE, mappedTrack.genre)
        assertEquals(ARTIST_ID, mappedTrack.artistId)
        assertEquals(TRACK_ID, mappedTrack.id)
        assertEquals(TRACK_NAME, mappedTrack.name)
        assertEquals(TRACK_ARTWORK_URL, mappedTrack.cover)
    }

}
