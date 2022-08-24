package com.globus.testkmm.android.di

import com.globus.testkmm.feature.news.NewsViewModel
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val androidModule = DI.Module("Android module") {
    bindProvider { NewsViewModel(newsRepository = instance()) }
}