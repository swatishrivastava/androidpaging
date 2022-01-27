package com.assignment.androidgithubusers.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.assignment.androidgithubusers.network.GithubApi

class GitUserViewModelFactory(
    private val api: GithubApi
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersListViewModel(api) as T
    }
}