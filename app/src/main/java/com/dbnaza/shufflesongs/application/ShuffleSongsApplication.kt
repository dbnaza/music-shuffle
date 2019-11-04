package com.dbnaza.shufflesongs.application

import android.app.Application
import com.dbnaza.shufflesongs.BuildConfig
import com.dbnaza.shufflesongs.di.allModules
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

@FlowPreview
class ShuffleSongsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupInjector()
        setupTimber()
    }

    private fun setupInjector() {
        startKoin {
            androidLogger()
            androidContext(this@ShuffleSongsApplication)
            modules(allModules)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object {
        const val MAX_SONGS_LIMIT = 5
    }
}