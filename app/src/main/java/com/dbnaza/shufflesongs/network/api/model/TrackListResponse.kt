package com.dbnaza.shufflesongs.network.api.model

import com.google.gson.annotations.SerializedName

data class TrackListResponse(
    @SerializedName("results")
    val results: List<TrackResponse>,
    @SerializedName("resultCount")
    val resultCount: Int
)

data class TrackResponse(
    @SerializedName("id")
    val id: Long,
    @SerializedName("wrapperType")
    val wrapperType: String,
    @SerializedName("artistType")
    val artistType: String? = null,
    @SerializedName("primaryGenreName")
    val primaryGenreName: String,
    @SerializedName("artistName")
    val artistName: String,
    @SerializedName("trackName")
    val trackName: String? = null,
    @SerializedName("country")
    val country: String? = null,
    @SerializedName("artworkUrl")
    val artworkUrl: String? = null,
    @SerializedName("releaseDate")
    val releaseDate: String? = null,
    @SerializedName("artistId")
    val artistId: Long? = null,
    @SerializedName("trackTimeMillis")
    val trackTimeMillis: Long? = null,
    @SerializedName("collectionName")
    val collectionName: String? = null,
    @SerializedName("trackExplicitness")
    val trackExplicitness: String? = null,
    @SerializedName("trackCensoredName")
    val trackCensoredName: String? = null,
    @SerializedName("collectionId")
    val collectionId: Long? = null
)