package com.dbnaza.shufflesongs.model

data class Artist(
    val id: Long,
    val name: String,
    val artistType: String,
    val genre: String,
    var tracks: MutableList<Track> = mutableListOf()
)