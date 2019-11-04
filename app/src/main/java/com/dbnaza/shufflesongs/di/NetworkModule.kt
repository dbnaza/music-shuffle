package com.dbnaza.shufflesongs.di

import com.dbnaza.shufflesongs.network.ApiClientBuilder
import com.dbnaza.shufflesongs.network.api.TracksApi
import com.dbnaza.shufflesongs.network.interactors.TracksInteractor
import org.koin.dsl.module

private const val TRACKS_API_BASE_URL = "https://us-central1-tw-exercicio-mobile.cloudfunctions.net/"

val networkModule = module {
    single {
        ApiClientBuilder.createService(
                TracksApi::class.java,
                TRACKS_API_BASE_URL
        )
    }
}
