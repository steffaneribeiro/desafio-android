package com.picpay.desafio.android.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.ui.adapterUser.UserListAdapter
import com.picpay.desafio.android.util.BaseModelState


class HomeFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar

    val adapter: UserListAdapter by lazy { UserListAdapter() }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        progressBar = view.findViewById(R.id.user_list_progress_bar)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservable()
        setAdapter()

    }

    private fun setObservable() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it?.status) {
                BaseModelState.STATUS.ERROR -> {
                    statusError(it.error)
                }

                BaseModelState.STATUS.SUCCESS -> {
                    it.data?.let { it1 -> statusSuccess(it1) }
                }

                BaseModelState.STATUS.LOADING -> {
                    statusLoading()
                }
            }
        })
    }

    private fun setAdapter() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

    private fun statusSuccess(userList: List<User>) {
        adapter.users = userList
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun statusError(error: Throwable?) {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.GONE
        Log.i("ERROR", error?.message.toString())
        Toast.makeText(context, getString(R.string.error), Toast.LENGTH_LONG).show()
    }

    private fun statusLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

}