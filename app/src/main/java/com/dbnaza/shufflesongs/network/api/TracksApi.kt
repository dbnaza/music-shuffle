package com.dbnaza.shufflesongs.network.api

import com.dbnaza.shufflesongs.application.ShuffleSongsApplication.Companion.MAX_SONGS_LIMIT
import com.dbnaza.shufflesongs.network.api.model.TrackListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TracksApi {
    @GET("lookup")
    fun getTracks(
        @Query("id") ids: String,
        @Query("limit") limit: Int = MAX_SONGS_LIMIT
    ): Call<TrackListResponse>
}
