package com.globus.testkmm.feature.news

import com.globus.testkmm.base.ViewModel
import com.globus.testkmm.data.repository.NewsRepository
import com.globus.testkmm.model.Article
import com.globus.testkmm.paging.DefaultPagingDataSource
import com.globus.testkmm.paging.Pager

class NewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    private val query = "moscow"
    private val pager = Pager<Article>(
        scope = getViewModelScope(),
        dataSource = DefaultPagingDataSource { pageNumber, pageSize ->
            newsRepository.loadNews(query = query, pageSize = pageSize, pageNumber = pageNumber)
        },
        compareItems = ::compareArticles
    )

    fun initialLoad() {
        pager.initLoad()
    }

    fun onReachEnd() {
        pager.loadNextPage()
    }

    fun data() = pager.dataFlow

    private fun compareArticles(old: Article, new: Article): Boolean =
        old.description == new.description

}