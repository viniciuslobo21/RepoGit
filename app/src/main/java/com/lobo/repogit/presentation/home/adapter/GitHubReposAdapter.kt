package com.lobo.repogit.presentation.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lobo.repogit.R
import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.core.extension.gone
import com.lobo.repogit.core.extension.loadFromUrl
import com.lobo.repogit.core.extension.visible
import com.lobo.repogit.databinding.ItemProgressBinding
import com.lobo.repogit.databinding.RepoItemLayoutBinding
import com.lobo.repogit.presentation.model.ItemPresentation
import kotlinx.android.synthetic.main.repo_item_layout.view.*

class GitHubReposAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val repoList = arrayListOf<ItemPresentation>()
    private val viewTypeItem = 0
    private val viewTypeLoading = 1

    var isLoading = false
    var isFullyLoaded = false

    fun updateList(list: List<ItemPresentation>) {
        repoList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == viewTypeItem)
            PopulateViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_item_layout, parent, false)
            )
        else
            LoadingViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_progress, parent, false)
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PopulateViewHolder) {
            holder.bind(repoList[position])
        } else if (holder is LoadingViewHolder) {
            holder.bind(isFullyLoaded)
        }
    }

    override fun getItemCount() = if (repoList.isNotEmpty()) repoList.size + 1
    else RepoGitConstants.NO_VALUE_INT

    override fun getItemViewType(position: Int): Int {
        return if (position == repoList.size) viewTypeLoading else viewTypeItem
    }

    override fun getItemId(position: Int): Long {
        return if (getItemViewType(position) == viewTypeItem) position.toLong()
        else -1
    }

    inner class PopulateViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var binding: RepoItemLayoutBinding? = null

        fun bind(item: ItemPresentation?) {

            binding = DataBindingUtil.bind(itemView)
            binding?.repo = item

            item?.owner?.avatar_url?.let { binding?.profileImagePoster?.loadFromUrl(it) }

        }

    }

    inner class LoadingViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private var binding: ItemProgressBinding? = null

        fun bind(isFullyLoaded: Boolean) {
            binding = DataBindingUtil.bind(itemView)
            if (isFullyLoaded) {
                binding?.loadMoreProgress?.gone()
                binding?.tvFullyLoaded?.visible()
            }
        }
    }

}