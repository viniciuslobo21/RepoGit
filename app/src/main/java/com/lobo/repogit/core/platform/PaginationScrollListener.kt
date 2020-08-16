package com.lobo.repogit.core.platform

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


abstract class PaginationScrollListener(private val mLinearLayoutManager: LinearLayoutManager) :
    RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private val visibleThreshold = 0

    var firstVisibleItem = 0
    var visibleItemCount = 0
    var totalItemCount = 0

    private var current_page = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager.itemCount
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition()

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && totalItemCount - visibleItemCount
            <= firstVisibleItem + visibleThreshold
        ) {
            current_page++
            onLoadMore(current_page)
            loading = true
        }
    }

    abstract fun onLoadMore(current_page: Int)

}

//abstract class PaginationScrollListener(private var layoutManager: LinearLayoutManager) :
//    RecyclerView.OnScrollListener() {
//
//    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//        super.onScrolled(recyclerView, dx, dy)
//        val visibleItemCount: Int = layoutManager.childCount
//        val totalItemCount: Int = layoutManager.itemCount
//        val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
//
//        if (!isLoading) {
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                && firstVisibleItemPosition >= 0
//            ) {
//                loadMoreItems()
//            }
//        }
//    }
//
//    protected abstract fun loadMoreItems()
//
//    abstract var isLoading: Boolean
//
//}
