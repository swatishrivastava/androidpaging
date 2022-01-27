package com.assignment.androidgithubusers.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.assignment.androidgithubusers.network.GithubApi

class GitUserDataSource(private val api: GithubApi) :
    PagingSource<Int, GitUsersDataItem>() {
    private var sinceCount = 0

    override fun getRefreshKey(state: PagingState<Int, GitUsersDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>):
            LoadResult<Int, GitUsersDataItem> {
        return try {
            var nextPageNumber = params.key ?: 0
            val response = api.getAllGithubUsers(sinceCount, params.loadSize)
            sinceCount = response[response.size-1].id
            LoadResult.Page(
                data = response,
                prevKey = if (nextPageNumber > 0) nextPageNumber - 1 else null,
                nextKey = if (response == null) null else nextPageNumber + 1
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}