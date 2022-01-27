package com.assignment.androidgithubusers.network

import com.assignment.androidgithubusers.data.GitUserResponse
import com.assignment.androidgithubusers.data.GitUsersDataItem
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("users")
     suspend fun getAllGithubUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") per_page: Int = 10
    ): List<GitUsersDataItem>
}