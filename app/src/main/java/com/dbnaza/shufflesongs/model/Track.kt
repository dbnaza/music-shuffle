package com.dbnaza.shufflesongs.model

data class Track(
    val id: Long,
    val name: String,
    var artistName: String,
    val artistId: Long,
    val genre: String,
    val cover: String
)