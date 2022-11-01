package com.scrumlaunch.daggerhilt.presentation.mainscreen.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.scrumlaunch.daggerhilt.R
import com.scrumlaunch.daggerhilt.databinding.RepositoryListItemBinding
import com.scrumlaunch.daggerhilt.domain.model.Item
import com.scrumlaunch.daggerhilt.util.NavigationUtil
import com.scrumlaunch.daggerhilt.util.getRandomColor
import com.scrumlaunch.daggerhilt.util.hide
import com.scrumlaunch.daggerhilt.util.setSafeOnClickListener

class RepositoriesRVAdapter :
    RecyclerView.Adapter<RepositoriesRVAdapter.RepositoriesRVViewHolder>() {

    private var list: MutableList<Item> = mutableListOf()

    fun setList(newList: List<Item>) {
        val oldPosition = list.size
        list.addAll(newList)
        notifyItemRangeInserted(oldPosition, newList.size)
    }

    fun clearList() {
        list.clear()
        notifyDataSetChanged()
    }


    inner class RepositoriesRVViewHolder(private val binding: RepositoryListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Item) {
            binding.apply {
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
                root.setSafeOnClickListener {
                    clickListener?.invoke(model)
                }
                authorImg.setSafeOnClickListener {
                    model.owner?.htmlUrl?.let {
                        NavigationUtil.navigateToBrowser(binding.root.context, it.trim())
                    }

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesRVViewHolder {
        val binding: RepositoryListItemBinding =
            RepositoryListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoriesRVViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesRVViewHolder, position: Int) {
        list.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = list.size

    private var clickListener: ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit) {
        clickListener = listener
    }
}