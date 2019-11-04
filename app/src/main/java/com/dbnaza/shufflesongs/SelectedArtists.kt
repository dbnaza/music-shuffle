package com.dbnaza.shufflesongs

object SelectedArtists {
    private const val JOHN_DOLLAR = "909253"
    private const val CHARLIE_AND_THE_CHEWIE_HUMANS = "1171421960"
    private const val BLOCO_TOTIOQUE = "358714030"
    private const val MC_ARIANNE = "1419227"
    private const val DECIMAIS_MCS = "264111789"

    val artists = mutableListOf(
        JOHN_DOLLAR, CHARLIE_AND_THE_CHEWIE_HUMANS,
        BLOCO_TOTIOQUE, MC_ARIANNE, DECIMAIS_MCS).joinToString(separator = ",")
}