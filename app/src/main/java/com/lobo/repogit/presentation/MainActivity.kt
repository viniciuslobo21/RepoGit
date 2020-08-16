package com.lobo.repogit.presentation


import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lobo.repogit.R
import com.lobo.repogit.core.extension.gone
import com.lobo.repogit.core.platform.BaseActivity
import com.lobo.repogit.core.platform.PaginationScrollListener
import com.lobo.repogit.core.platform.fold
import com.lobo.repogit.databinding.ActivityMainBinding
import com.lobo.repogit.presentation.home.HomeViewModel
import com.lobo.repogit.presentation.home.adapter.GitHubReposAdapter
import com.lobo.repogit.presentation.model.RepoInformationPresentation
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val viewModel: HomeViewModel by viewModel()

    lateinit var adapter: GitHubReposAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var rv: RecyclerView
    lateinit var progressBar: ProgressBar

    private var PAGE_START = 1
    private var IS_LOADING = false

    private var currentPage = PAGE_START

    override fun getContentLayoutId() = R.layout.activity_main

    override fun init() {
        subscribeToLiveData()
        setupAdapter()
        loadFirstPage()
    }

    private fun setupAdapter() {
        rv = binding.rvRepoList
        progressBar = binding.mainProgress

        adapter = GitHubReposAdapter(resourceHelper.getContext())
        linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = adapter

        rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
//            override fun loadMoreItems() {
//                IS_LOADING = true
//                currentPage += 1
//                loadNextPage(currentPage)
//            }
//
//            override var isLoading = IS_LOADING

            override fun onLoadMore(current_page: Int) {
                loadNextPage(current_page)
            }
        })
    }

    private fun loadFirstPage() {
        viewModel.getTopRepos(PAGE_START)
    }

    private fun subscribeToLiveData() {
        viewModel.topRepoLiveData.observe(this, Observer {
            it.fold(this::handleError) { list ->
                handleTopRepoSuccess(list)
            }
        })
    }

    private fun handleTopRepoSuccess(repoInformationPresentation: RepoInformationPresentation) {
        IS_LOADING = false
        binding.mainProgress.gone()
        adapter.addAll(repoInformationPresentation.items)
//        adapter.addLoadingFooter()
    }


    private fun loadNextPage(current_page: Int) {
        IS_LOADING = false
        viewModel.getTopRepos(current_page)
    }
}
