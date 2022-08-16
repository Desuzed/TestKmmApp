package com.globus.testkmm.data.repository

import com.globus.testkmm.data.network.HttpClientFactory
import com.globus.testkmm.model.Article
import com.globus.testkmm.model.NewsItem

class NewsRepository(private val httpClientFactory: HttpClientFactory) {
    suspend fun loadNews(url: String): List<Article> {
        return httpClientFactory.get<NewsItem>(url).articles
    }
}