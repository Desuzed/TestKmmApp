package com.globus.testkmm.di

import com.globus.testkmm.base.ViewModelFactory
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val androidModule = DI.Module("Android module") {
    //bindProvider { NewsViewModel(newsRepository = instance()) }
    bindProvider { ViewModelFactory(newsRepository = instance()) }
}