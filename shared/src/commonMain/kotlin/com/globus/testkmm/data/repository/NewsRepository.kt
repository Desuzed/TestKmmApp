package com.globus.testkmm.data.repository

import com.globus.testkmm.data.network.HttpClientFactory
import com.globus.testkmm.model.Article
import com.globus.testkmm.model.NewsItem

class NewsRepository(private val httpClientFactory: HttpClientFactory, private val  newsApi: NewsApi) {
    suspend fun loadNews(query: String, pageNumber: Int, pageSize: Int): List<Article> {
        val url = newsApi.concatUrl(query=query, pageSize = pageSize, page = pageNumber)
        return httpClientFactory.get<NewsItem>(url).articles
    }
}