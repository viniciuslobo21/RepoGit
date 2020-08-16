package com.lobo.repogit.presentation.home.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.lobo.repogit.R
import com.lobo.repogit.core.extension.gone
import com.lobo.repogit.presentation.model.ItemPresentation
import kotlinx.android.synthetic.main.container_layout.view.*
import kotlinx.android.synthetic.main.repo_item_layout.view.*

class GitHubReposAdapter(
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM = 0
    private val LOADING = 1

    private var isLoadingAdded = false

    private val repoList = arrayListOf<ItemPresentation>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)

        when (viewType) {
            ITEM -> viewHolder =
                getViewHolder(parent, inflater)
            LOADING -> {
                val v2: View = inflater.inflate(R.layout.item_progress, parent, false)
                viewHolder = LoadingViewHolder(v2)
            }
        }
        return viewHolder!!
    }

    override fun getItemCount() = repoList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == repoList.size - 1 && isLoadingAdded)
            LOADING
        else
            ITEM
    }

    fun add(r: ItemPresentation) {
        repoList.add(r)
        notifyItemInserted(repoList.size - 1)
    }

    fun addAll(items: List<ItemPresentation>) {
        for (item in items) {
            add(item)
        }
    }

    fun remove(r: ItemPresentation) {
        val position: Int = repoList.indexOf(r)
        if (position > -1) {
            repoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        isLoadingAdded = false
        while (itemCount > 0) {
            getItem(0)?.let { remove(it) }
        }
    }

    fun isEmpty(): Boolean {
        return itemCount == 0
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ItemPresentation())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = repoList.size - 1
        val result: ItemPresentation? = getItem(position)
        if (result != null) {
            repoList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): ItemPresentation? {
        return repoList[position]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = repoList[position]

        when (getItemViewType(position)) {
            ITEM -> {
                val gitHubVH: GitHubReposViewHolder = holder as GitHubReposViewHolder

                gitHubVH.descriptionStar.text = "Star"
                gitHubVH.descriptionFork.text = "Fork"
                gitHubVH.imageStar.setImageResource(R.drawable.ic_star)
                gitHubVH.repoMaininfo.text = item.owner.login + "/" + item.name
                gitHubVH.starsCount.text = item.stargazersCount.toString()
                gitHubVH.imageFork.setImageResource(R.drawable.fork)
                gitHubVH.forkCount.text = item.forksCount.toString()

                Glide
                    .with(context)
                    .load(item.owner.avatar_url)
                    .listener(object : RequestListener<Drawable?> {
                        override fun onLoadFailed(
                            @Nullable e: GlideException?,
                            model: Any,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            gitHubVH.imageProgress.gone()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable?>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            gitHubVH.imageProgress.gone()
                            return false
                        }
                    })
                    .apply(
                        RequestOptions()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .centerCrop()
                            .dontTransform()
                    )
                    .into(gitHubVH.imageProfile)
            }
            LOADING -> {
                val teste = "teste"
            }

        }
    }

    private fun getViewHolder(
        parent: ViewGroup,
        inflater: LayoutInflater
    ): RecyclerView.ViewHolder? {
        val viewHolder: RecyclerView.ViewHolder
        val v1: View = inflater.inflate(R.layout.repo_item_layout, parent, false)
        viewHolder = GitHubReposViewHolder(v1)
        return viewHolder
    }

    protected class GitHubReposViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        val imageProfile = itemView.profile_image_poster
        val repoMaininfo = itemView.repo_main_info

        val imageProgress = itemView.profile_image_progress
        val imageStar = itemView.container_star.ic_star
        val descriptionStar = itemView.container_star.tv_image_description
        val starsCount = itemView.container_star.stars_counter

        val imageFork = itemView.container_fork.ic_star
        val descriptionFork = itemView.container_fork.tv_image_description
        val forkCount = itemView.container_fork.stars_counter

    }

    protected class LoadingViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)

}