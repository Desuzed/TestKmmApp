package com.globus.testkmm.di

import com.globus.testkmm.data.network.HttpClientFactory
import com.globus.testkmm.data.repository.NewsApi
import com.globus.testkmm.data.repository.NewsRepository
import org.kodein.di.*

val repositoryModule = DI.Module("Repository module") {

    bind<HttpClientFactory> { singleton { HttpClientFactory } }
    bind<NewsApi> { singleton { NewsApi } }
    bind<NewsRepository> {
        singleton {
            NewsRepository(
                httpClientFactory = instance(),
                newsApi = instance()
            )
        }
    }

}

//val viewModelModule = DI.Module {
//    bind<NewsViewModel> { provider { NewsViewModel(newsRepository = instance()) } }
//}