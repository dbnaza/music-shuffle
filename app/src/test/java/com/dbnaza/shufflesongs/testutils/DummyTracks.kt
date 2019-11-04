package com.dbnaza.shufflesongs.testutils

import com.dbnaza.shufflesongs.model.Artist
import com.dbnaza.shufflesongs.model.Track
import kotlin.random.Random

val DUMMY_GENRES = listOf("Rock", "Hip-Hop", "Axé", "Pop", "Metal")

val DUMMY_ARTISTS_NAMES = listOf(
    "John Dollar",
    "Decimais MCs",
    "Totioque",
    "MC Arianne",
    "Charlie and the Chewie Humans"
)

fun getDummyTracks(artistName: String,
                   artistId: Long,
                   genre: String): MutableList<Track> {
    val list = mutableListOf<Track>()

    for (i in 0..4) {
        val track = Track(
            id = i.toLong(),
            name = "Track ${i + 1}",
            artistName = artistName,
            artistId = artistId,
            genre = genre,
            cover = Random.nextLong().toString()
        )
        list.add(track)
    }

    return list
}

val DUMMY_ARTISTS = run {
    val list = mutableListOf<Artist>()

    for (i in 0..4) {
        val name = DUMMY_ARTISTS_NAMES[i]
        val genre = DUMMY_GENRES[i]
        val id = Random.nextLong()
        val artist = Artist(
            id = id,
            name = name,
            artistType = "Artist",
            genre = genre,
            tracks = getDummyTracks(name, id, genre)
        )
        list.add(artist)
    }
    list
}
