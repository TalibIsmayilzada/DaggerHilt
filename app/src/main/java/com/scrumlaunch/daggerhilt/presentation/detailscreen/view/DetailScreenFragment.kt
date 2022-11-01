package com.scrumlaunch.daggerhilt.presentation.detailscreen.view

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import coil.load
import coil.transform.CircleCropTransformation
import com.scrumlaunch.daggerhilt.R
import com.scrumlaunch.daggerhilt.databinding.DetailscreenFragmentBinding
import com.scrumlaunch.daggerhilt.domain.model.Item
import com.scrumlaunch.daggerhilt.presentation.mainscreen.view.MainScreenFragment
import com.scrumlaunch.daggerhilt.presentation.ui.view.BaseVBFragment
import com.scrumlaunch.daggerhilt.util.NavigationUtil
import com.scrumlaunch.daggerhilt.util.getRandomColor
import com.scrumlaunch.daggerhilt.util.hide
import com.scrumlaunch.daggerhilt.util.setSafeOnClickListener
import com.scrumlaunch.daggerhilt.workmanager.CounterWorkManager

class DetailScreenFragment :
    BaseVBFragment<DetailscreenFragmentBinding>(R.layout.detailscreen_fragment) {

    override fun setUpViews() {

        arguments?.getParcelable<Item>(MainScreenFragment.ITEM_KEY)?.let { model ->
            binding.apply {
                detailToolbar.setNavigationOnClickListener { activity?.onBackPressed() }
                detailToolbar.setOnMenuItemClickListener { item ->
                    when (item?.itemId) {
                        R.id.startWork -> startWork()
                        R.id.go -> NavigationUtil.navigateToBrowser(
                            requireContext(), model.htmlUrl ?: ""
                        )
                    }
                    true
                }


                val topicList =
                    if (model.topics?.isNotEmpty() == true) "Topics: ${model.topics}" else ""
                "${model.description}\n${model.createdAt}\n$topicList".also { desc.text = it }

                authorImg.load(model.owner?.avatarUrl) {
                    placeholder(R.drawable.ic_mark_github_24)
                    error(R.drawable.ic_mark_github_24)
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                model.language?.let {
                    circle.setColorFilter(getRandomColor())
                    language.text = it
                } ?: run {
                    language.hide()
                    circle.hide()
                }
                repositoryName.text = model.name
                authorName.text = model.owner?.login
                issueCount.text = model.openIssuesCount.toString()
                starCount.text = model.watchersCount.toString()
                forkCount.text = model.forksCount.toString()
                authorImg.setSafeOnClickListener {
                    model.owner?.htmlUrl?.let {
                        NavigationUtil.navigateToBrowser(requireContext(), it.trim())
                    }
                }
            }
        }
    }

    private fun startWork(){
        val data = Data.Builder()
        data.putString("data1","value1")
        val request = OneTimeWorkRequestBuilder<CounterWorkManager>().setInputData(data.build()).build()
        WorkManager.getInstance(requireContext().applicationContext).enqueue(request)
    }
}