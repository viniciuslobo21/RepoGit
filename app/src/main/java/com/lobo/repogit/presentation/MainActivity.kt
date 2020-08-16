package com.lobo.repogit.presentation


import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lobo.repogit.R
import com.lobo.repogit.core.extension.gone
import com.lobo.repogit.core.extension.invisible
import com.lobo.repogit.core.extension.visible
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

    override fun getContentLayoutId() = R.layout.activity_main

    override fun init() {
        subscribeToLiveData()
        setupAdapter()
        loadFirstPage()
    }

    private fun setupAdapter() {
        rv = binding.rvRepoList
        progressBar = binding.listProgress

        adapter = GitHubReposAdapter(resourceHelper.getContext())
        linearLayoutManager = LinearLayoutManager(this)
        rv.layoutManager = linearLayoutManager
        rv.itemAnimator = DefaultItemAnimator()
        rv.adapter = adapter

        rv.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
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
        adapter.addAll(repoInformationPresentation.items)
    }

    private fun loadNextPage(current_page: Int) {
        binding.listProgress.visible()
        Toast.makeText(resourceHelper.getContext(), "Loading page $current_page ...", Toast.LENGTH_LONG).show()
        viewModel.getTopRepos(current_page)
    }
}
