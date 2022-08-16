package com.globus.testkmm.data.repository

object NewsApi {

    private const val baseUrl = "https://newsapi.org/v2/everything"
    private const val apiKey = "5eb0bf747eba482791fed6e9a5d78e22"
    private const val baseParams = "?apiKey=$apiKey"

    fun concatUrl(query: String, pageSize: Int, page: Int): String {
        val customParameters = "&q=$query&pageSize=$pageSize&page=$page"
        return baseUrl + baseParams + customParameters
    }
}