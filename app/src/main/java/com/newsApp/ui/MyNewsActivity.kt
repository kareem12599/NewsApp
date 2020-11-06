package com.newsApp.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.newsApp.R
import com.newsApp.ViewModelFactory
import com.newsApp.domain.NewsUseCase
import com.newsApp.inject
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MyNewsActivity : AppCompatActivity() {
    @Inject
    lateinit var useCase: NewsUseCase

    private val viewModel by viewModels<NewsViewModel> { getViewModelFactory() }

    private fun getViewModelFactory(): ViewModelFactory {
        return ViewModelFactory(useCase, this)

    }


    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inject()
        initViewModel()
        initAdapter()


    }

    private fun initViewModel() {

        viewModel.news.observe(this , Observer {items->
            myAdapter.setNewsItems(items)
        })
        viewModel.dataLoading.observe(this , Observer {dataLoaded ->
            if (dataLoaded && swiperefresh.isRefreshing){
                swiperefresh.isRefreshing = false
            }
        })
        swiperefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initAdapter() {
        recyclerView = findViewById(R.id.newsRecyclerView)
        myAdapter = NewsAdapter()
        with(recyclerView) {
            adapter = myAdapter
            layoutManager = LinearLayoutManager(this@MyNewsActivity)
        }
    }


}


