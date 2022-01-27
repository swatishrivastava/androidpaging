package com.assignment.androidgithubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.assignment.androidgithubusers.data.GitUsersDataItem
import com.assignment.androidgithubusers.databinding.GithubUserItemBinding
import com.assignment.androidgithubusers.loadImage
import java.util.*
import android.R
import android.view.View
import com.assignment.androidgithubusers.databinding.GithubUserItemGridBinding


private const val LIST_ITEM = 0
private const val GRID_ITEM = 1


class GitHubUserListAdapter(diffCallback: DiffUtil.ItemCallback<GitUsersDataItem>) :
    PagingDataAdapter<GitUsersDataItem,
            RecyclerView.ViewHolder>(diffCallback) {
    var isSwitchView = true

    inner class ListViewHolder(private val binding: GithubUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindUser(item: GitUsersDataItem) = with(binding) {
            userImageView.loadImage(item.avatar_url)
            userName.text = item.login.uppercase(Locale.getDefault())
        }
    }

    inner class GridViewHolder(private val binding: GithubUserItemGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindUser(item: GitUsersDataItem) = with(binding) {
            userImageView.loadImage(item.avatar_url)
            userName.text = item.login.uppercase(Locale.getDefault())
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        var item = getItem(position)
        item?.let {
            if (viewHolder is ListViewHolder) {
                viewHolder.bindUser(it)
            } else if (viewHolder is GridViewHolder) {
                viewHolder.bindUser(it)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): RecyclerView.ViewHolder {
        return if (i == LIST_ITEM) {
            ListViewHolder(
                GithubUserItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            GridViewHolder(
                GithubUserItemGridBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }


    }

    object GitUserComparator : DiffUtil.ItemCallback<GitUsersDataItem>() {
        override fun areItemsTheSame(
            oldItem: GitUsersDataItem,
            newItem: GitUsersDataItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: GitUsersDataItem,
            newItem: GitUsersDataItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    fun toggleItemViewType(): Boolean {
        isSwitchView = !isSwitchView
        return isSwitchView
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSwitchView){
            LIST_ITEM;
        }else{
            GRID_ITEM;
        }
    }
}