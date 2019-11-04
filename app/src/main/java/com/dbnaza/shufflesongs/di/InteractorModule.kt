package com.dbnaza.shufflesongs.di

import com.dbnaza.shufflesongs.network.api.TrackMapper
import com.dbnaza.shufflesongs.network.interactors.TracksInteractor
import org.koin.dsl.module

val tracksInteractorModule = module {
    factory { TrackMapper() }
    factory { TracksInteractor(get(), get()) }
}