package com.scrumlaunch.daggerhilt.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerPagingScrollListener(private val pagingListener: PagingListener?) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
        val totalItemCount = linearLayoutManager!!.itemCount
        val lastVisible = linearLayoutManager.findLastVisibleItemPosition()
        val endHasBeenReached = lastVisible + 1 >= totalItemCount

        if (totalItemCount > 0 && endHasBeenReached) {
            //bottom of list!
            pagingListener?.loadMore()
        }

    }
}

interface PagingListener {
    fun loadMore()
}