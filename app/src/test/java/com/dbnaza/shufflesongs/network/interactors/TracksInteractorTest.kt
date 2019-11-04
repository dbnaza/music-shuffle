package com.dbnaza.shufflesongs.network.interactors

import com.dbnaza.shufflesongs.SelectedArtists
import com.dbnaza.shufflesongs.network.api.TrackMapper
import com.dbnaza.shufflesongs.network.ApiClientBuilder
import com.dbnaza.shufflesongs.network.ArtistsWithSongsListViewState
import com.dbnaza.shufflesongs.network.api.TracksApi
import com.dbnaza.shufflesongs.network.api.model.TrackListResponse
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TracksInteractorTest {

    private lateinit var server: MockWebServer
    private lateinit var tracksApi: TracksApi
    private lateinit var interactor: TracksInteractor
    private val mapper = TrackMapper()

    @Before
    fun setUp() {
        server = MockWebServer()
        server.start()
        val url = server.url("/").toString()
        tracksApi = ApiClientBuilder.createService(TracksApi::class.java, url)
        MockKAnnotations.init(this)

        interactor = TracksInteractor(tracksApi, mapper)
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getTracksWithSuccess_thenReturnSuccessState() = runBlocking {
        val tracksResponse = loadJsonFromResources(
            javaClass.classLoader!!,
            "tracks_response_200.json"
        )
        server.enqueue200Response(tracksResponse)

        val tracks = interactor.getArtistsWithSongs(SelectedArtists.artists)

        assertTrue(tracks is  ArtistsWithSongsListViewState.SuccessState)
    }

    @Test
    fun getTracksWithSuccessButEmpty_thenReturnEmptyState() = runBlocking {
        val tracksResponse = TrackListResponse(listOf(), 0)
        server.enqueue200Response(Gson().toJson(tracksResponse))

        val tracks = interactor.getArtistsWithSongs(SelectedArtists.artists)

        assertTrue(tracks is  ArtistsWithSongsListViewState.EmptyState)
    }

    @Test
    fun getTracksWithError_thenReturnErrorState() = runBlocking {
        val tracksResponse = "{\"message\": \"Error\"}"
        server.enqueueIOError(tracksResponse)

        val tracks = interactor.getArtistsWithSongs(SelectedArtists.artists)

        assertTrue(tracks is  ArtistsWithSongsListViewState.ErrorState)
    }
}