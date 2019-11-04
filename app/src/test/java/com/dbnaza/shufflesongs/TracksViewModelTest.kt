package com.dbnaza.shufflesongs

import androidx.lifecycle.Observer
import com.dbnaza.shufflesongs.model.Track
import com.dbnaza.shufflesongs.network.ArtistsWithSongsListViewState
import com.dbnaza.shufflesongs.network.interactors.TracksInteractor
import com.dbnaza.shufflesongs.testutils.DUMMY_ARTISTS
import com.dbnaza.shufflesongs.testutils.rule.instantLiveDataAndCoroutineRules
import com.dbnaza.shufflesongs.ui.viewmodel.TracksViewModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import java.lang.Exception
import com.dbnaza.shufflesongs.util.shuffleArtist
import com.dbnaza.shufflesongs.util.shuffleTrack

@FlowPreview
class TracksViewModelTest {
    @get:Rule
    val rule = instantLiveDataAndCoroutineRules

    private lateinit var viewModel: TracksViewModel
    @MockK
    private lateinit var tracksInteractor: TracksInteractor
    @RelaxedMockK
    private lateinit var observerTrackList: Observer<List<Track>>
    @RelaxedMockK
    private lateinit var observerState: Observer<ArtistsWithSongsListViewState>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = TracksViewModel(tracksInteractor)
        with(viewModel) {
            tracksLiveData.observeForever(observerTrackList)
            stateLiveData.observeForever(observerState)
        }
    }

    @After
    fun tearDown() {
        with(viewModel) {
            tracksLiveData.removeObserver(observerTrackList)
            stateLiveData.removeObserver(observerState)
        }
    }

    @Test
    fun whenGetTracks_shouldEmitLoadingState() = runBlocking {
        val successState = ArtistsWithSongsListViewState.SuccessState(
            DUMMY_ARTISTS
        )
        coEvery { tracksInteractor.getArtistsWithSongs(SelectedArtists.artists) } returns successState

        viewModel.getTracks()

        verify {
            observerState.onChanged(ArtistsWithSongsListViewState.LoadingState)
        }
    }

    @Test
    fun whenGetTracks_withSuccessAndListNotEmpty_shouldEmitSuccessState() = runBlocking {
        val successState = ArtistsWithSongsListViewState.SuccessState(
            DUMMY_ARTISTS
        )
        coEvery { tracksInteractor.getArtistsWithSongs(SelectedArtists.artists) } returns successState

        viewModel.getTracks()

        verify {
            observerState.onChanged(successState)
        }
    }


    @Test
    fun whenGetTracks_withSuccessAndListNotEmpty_shouldEmitShuffledList() = runBlocking {
        val successState = ArtistsWithSongsListViewState.SuccessState(
            DUMMY_ARTISTS
        )
        coEvery { tracksInteractor.getArtistsWithSongs(SelectedArtists.artists) } returns successState

        mockkStatic("com.dbnaza.shufflesongs.util.CollectionExtKt")
        every {DUMMY_ARTISTS.shuffleArtist() } returns DUMMY_ARTISTS
        for (i in 0 until DUMMY_ARTISTS.size) {
            every { DUMMY_ARTISTS[i].tracks.shuffleTrack() } returns DUMMY_ARTISTS[i].tracks
        }
        val shuffled = viewModel.getArtistsTracksShuffledList(DUMMY_ARTISTS)

        viewModel.getTracks()

        verify {
            observerTrackList.onChanged(shuffled)
        }
    }

    @Test
    fun whenGetTracks_withError_shouldEmitErrorState() = runBlocking {
        val errorState = ArtistsWithSongsListViewState.ErrorState(Exception("Error"))
        coEvery { tracksInteractor.getArtistsWithSongs(SelectedArtists.artists) } returns errorState

        viewModel.getTracks()

        verify {
            observerState.onChanged(errorState)
        }
    }

    @Test
    fun whenGetTracks_withSuccessAndListEmpty_shouldEmitEmptyState() = runBlocking {
        val emptyState = ArtistsWithSongsListViewState.EmptyState
        coEvery { tracksInteractor.getArtistsWithSongs(SelectedArtists.artists) } returns emptyState

        viewModel.getTracks()

        verify {
            observerState.onChanged(emptyState)
        }
    }

}
