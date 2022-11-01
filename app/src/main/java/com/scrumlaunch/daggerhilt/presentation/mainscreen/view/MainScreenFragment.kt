package com.scrumlaunch.daggerhilt.presentation.mainscreen.view

import android.view.Gravity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.scrumlaunch.daggerhilt.R
import com.scrumlaunch.daggerhilt.databinding.MainscreenFragmentBinding
import com.scrumlaunch.daggerhilt.presentation.mainscreen.adapter.RepositoriesRVAdapter
import com.scrumlaunch.daggerhilt.presentation.mainscreen.viewmodel.MainScreenViewModel
import com.scrumlaunch.daggerhilt.presentation.ui.view.BaseMVVMFragment
import com.scrumlaunch.daggerhilt.util.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainScreenFragment : BaseMVVMFragment<MainscreenFragmentBinding, MainScreenViewModel>(
    R.layout.mainscreen_fragment, MainScreenViewModel::class.java
), SearchView.OnQueryTextListener {

    companion object {
        const val ITEM_KEY = "repoItem"
    }

    override var useSharedViewModel: Boolean = false

    private val mAdapter: RepositoriesRVAdapter by lazy { RepositoriesRVAdapter() }

    private val onScrollListener by lazy {
        RecyclerPagingScrollListener(pagingListener = object : PagingListener {
            override fun loadMore() {
                if (viewModel.getNextPage) {
                    search()
                }
            }
        })
    }

    override fun setUpViews() {
        binding.apply {

            searchToolbar.setOnMenuItemClickListener {
                val popupMenu = PopupMenu(requireContext(), searchToolbar, Gravity.END)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.stars -> viewModel.setSortType(MainScreenViewModel.SORT_BY_STARS)
                        R.id.forks -> viewModel.setSortType(MainScreenViewModel.SORT_BY_FORKS)
                        R.id.updated -> viewModel.setSortType(MainScreenViewModel.SORT_BY_UPDATED)
                    }
                    viewModel.resetPage()
                    mAdapter.clearList()
                    search()
                    true
                }
                popupMenu.inflate(R.menu.sort_inner_menu)
                popupMenu.show()
                true
            }

            searchBtn.setOnClickListener { search() }

            mAdapter.setOnItemClickListener {
                findNavController().navigate(
                    R.id.main_to_detail,
                    bundleOf(ITEM_KEY to it)
                )
            }

            searchView.setOnQueryTextListener(this@MainScreenFragment)
            repositoriesRV.adapter = mAdapter

            repositoriesRV.addOnScrollListener(onScrollListener)

            searchView.clearFocus()
        }
    }

    override fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.data.collectLatest {
                when (it) {
                    is ResultWrapper.Loading -> showLoading()
                    is ResultWrapper.Success -> {
                        hideLoading()
                        binding.errTxt.hide()
                        binding.repositoriesRV.show()
                        it.data?.body()?.items?.let { it1 -> mAdapter.setList(it1) }
                    }
                    is ResultWrapper.Error -> {
                        binding.errTxt.hide()
                        binding.errTxt.text = it.error.message
                        hideLoading()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun search() {
        binding.searchView.query?.let {
            val text = it.trim().toString()
            if (text.isNotEmpty()) {
                if (text != viewModel.searchText) {
                    mAdapter.clearList()
                }
                viewModel.search(text)
                hideKeyboard()
            }
        }
    }


    private fun showLoading() {
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.loading.hide()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        search()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}