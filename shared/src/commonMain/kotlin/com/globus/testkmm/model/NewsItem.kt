package com.globus.testkmm.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem (val status: String, val totalResults: Int, val articles: List<Article>)