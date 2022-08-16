package com.globus.testkmm.android

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.globus.testkmm.Greeting
import com.globus.testkmm.data.network.HttpClientFactory
import com.globus.testkmm.feature.news.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val httpClientFactory = HttpClientFactory
    private val greeting = Greeting(httpClientFactory)
    private val mainScope = MainScope()
    private val articleAdapter by lazy {
        ArticleAdapter {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newsViewModel: NewsViewModel =
            ViewModelProvider(this)[NewsViewModel::class.java]

        val recycler = findViewById<RecyclerView>(R.id.articleRecycler)
        with(recycler) {
            adapter = articleAdapter
            addOnChildAttachStateChangeListener(object :
                RecyclerView.OnChildAttachStateChangeListener {
                override fun onChildViewAttachedToWindow(view: View) {
                    val current = getChildAdapterPosition(view)
                    val last = articleAdapter.itemCount - 1

                    if (current == last) {
                        newsViewModel.onReachEnd()
                    }
                }

                override fun onChildViewDetachedFromWindow(view: View) {}

            })
        }

        mainScope.launch {
            newsViewModel.data().collect {
                articleAdapter.submitList(it)
            }
        }

        newsViewModel.initialLoad()
    }
}

