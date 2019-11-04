package com.dbnaza.shufflesongs.util

import com.dbnaza.shufflesongs.model.Artist
import com.dbnaza.shufflesongs.model.Track

fun List<Artist>.shuffleArtist() : MutableList<Artist> {
    val shuffled = this.copyArtist()
    return shuffled.shuffled().toMutableList()
}

fun List<Track>.shuffleTrack() : MutableList<Track> {
    val shuffled = this.copyTrack()
    return shuffled.shuffled().toMutableList()
}

fun List<Artist>.copyArtist() : List<Artist> {
    return ArrayList(this.map { it.copy() })
}

fun List<Track>.copyTrack() : List<Track> {
    return ArrayList(this.map { it.copy() })
}