package com.assignment.androidgithubusers.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.assignment.androidgithubusers.data.GitUserDataSource
import com.assignment.androidgithubusers.network.GithubApi

class UsersListViewModel(private val api: GithubApi) : ViewModel() {
    val gitUsers = Pager(PagingConfig(pageSize = 10)) {
        GitUserDataSource(api)
    }.flow.cachedIn(viewModelScope)


}