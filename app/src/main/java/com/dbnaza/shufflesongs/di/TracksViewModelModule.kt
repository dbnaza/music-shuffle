package com.dbnaza.shufflesongs.di

import com.dbnaza.shufflesongs.ui.viewmodel.TracksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tracksViewModelModule = module {
    viewModel { TracksViewModel(get()) }
}