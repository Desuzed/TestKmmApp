package com.globus.testkmm.feature.news

import com.globus.testkmm.base.ViewModel
import com.globus.testkmm.data.network.HttpClientFactory
import com.globus.testkmm.data.repository.NewsApi
import com.globus.testkmm.data.repository.NewsRepository
import com.globus.testkmm.model.Article
import dev.icerock.moko.mvvm.livedata.asFlow
import dev.icerock.moko.mvvm.livedata.data
import dev.icerock.moko.paging.LambdaPagedListDataSource
import dev.icerock.moko.paging.Pagination
import io.github.aakira.napier.Napier

class NewsViewModel : ViewModel() {
    private val newsRepository: NewsRepository = NewsRepository(HttpClientFactory)
    private val newsApi = NewsApi
    private val query = "moscow"

    private val pagination: Pagination<Article> = Pagination(
        parentScope = getViewModelScope(),
        dataSource = LambdaPagedListDataSource {
            val c = countPageNumber(it?.size ?: 0)
            val url = newsApi.concatUrl(query = query, pageSize = PAGE_SIZE.toInt(), page = c)
            newsRepository.loadNews(url)
        },
        comparator = Comparator { a: Article, b: Article ->
            a.description.compareTo(b.description)
        },
        nextPageListener = { result: Result<List<Article>> ->
            if (result.isSuccess) {
                Napier.v("Next page successful loaded")

            } else {
                Napier.v("Next page loading failed")
            }
        },
        refreshListener = { result: Result<List<Article>> ->
            if (result.isSuccess) {
                Napier.v("Refresh successful")
            } else {
                Napier.v("Refresh failed")
            }
        },
    )

    fun initialLoad() {
        pagination.loadFirstPage()
    }

    fun onReachEnd() {
        pagination.loadNextPage()

    }

    fun data() = pagination.state.data().asFlow()

    //Пришлось делать такой метод из - за апишки, которая может возвращать нуллы у любого поля,
    // в результате чего получалось не кратное PAGE_SIZE количество новостей

    private fun countPageNumber(size: Int): Int {
        if (size == 0) return 1
        val floatValue = size / PAGE_SIZE
        println("COUNT: floatValue =  ${floatValue}, size = $size")
        return if (floatValue < size / PAGE_SIZE.toInt()) {
            floatValue.toInt() + 2
        } else floatValue.toInt() + 1
    }

    companion object {
        const val PAGE_SIZE = 10.0F
    }
}