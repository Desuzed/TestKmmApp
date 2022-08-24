package com.globus.testkmm.android

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.globus.testkmm.di.DiApp
import com.globus.testkmm.feature.news.NewsViewModel
import com.globus.testkmm.paging.PagingState
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.viewmodel.viewModel

class MainActivity : AppCompatActivity(), DIAware {
    override val di: DI by lazy { DiApp.di() }
    private val newsViewModel: NewsViewModel by viewModel()
    private val mainScope = MainScope()
    private val articleAdapter by lazy {
        ArticleAdapter {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                when (it) {
                    is PagingState.Success -> {
                        articleAdapter.submitList(it.content)
                    }
                    is PagingState.Error -> {
                        Log.i("Paging", "Error: ${it.error}")
                    }
                    is PagingState.Loading -> {
                        Log.i("Paging", "Loading")
                    }
                    is PagingState.NoData -> {
                        Log.i("Paging", "NoData")
                    }
                }
            }
        }

        newsViewModel.initialLoad()
    }

}

