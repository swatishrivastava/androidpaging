package com.assignment.androidgithubusers.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.androidgithubusers.R
import com.assignment.androidgithubusers.adapter.GitHubUserListAdapter
import com.assignment.androidgithubusers.network.RetrofitClient
import kotlinx.android.synthetic.main.users_list_fragment.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.GridLayoutManager




class UsersListFragment : Fragment() {

    private lateinit var viewModel: UsersListViewModel
    private lateinit var userListAdapter: GitHubUserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val factory = GitUserViewModelFactory(RetrofitClient.api)
        userListAdapter = GitHubUserListAdapter(GitHubUserListAdapter.GitUserComparator)
        viewModel = ViewModelProvider(this, factory).get(UsersListViewModel::class.java)
        with(user_recycler_view) {

            adapter = userListAdapter
            val divider = DividerItemDecoration(
                context,
                LinearLayoutManager(context).orientation
            )
            addItemDecoration(divider)
        }

        lifecycleScope.launch {
            viewModel.gitUsers.collectLatest {
                userListAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_view -> {
                val isSwitched: Boolean = userListAdapter.toggleItemViewType()
                user_recycler_view.layoutManager = if (isSwitched) LinearLayoutManager(context)
                else GridLayoutManager(
                    context,
                    2
                )
                userListAdapter.notifyDataSetChanged()
            }

        }
        return true
    }

}