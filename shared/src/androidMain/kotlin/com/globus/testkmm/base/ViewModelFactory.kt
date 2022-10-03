package com.globus.testkmm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globus.testkmm.data.repository.NewsRepository
import com.globus.testkmm.feature.news.NewsViewModel

class ViewModelFactory(private val newsRepository: NewsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsViewModel::class.java) -> {
                NewsViewModel(newsRepository) as T
            }
            else -> {
                throw IllegalArgumentException("ViewModel Not Found")
            }
        }
    }
}