package com.apps.pixabayapp.ui.home.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.api.RetrofitBuilder
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.apps.pixabayapp.ui.base.ViewModelFactory
import com.apps.pixabayapp.ui.home.adapter.PhotoAdapter
import com.apps.pixabayapp.ui.home.viewmodel.HomeViewModel
import com.apps.pixabayapp.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        photoView.layoutManager = GridLayoutManager(this,2)
        adapter = PhotoAdapter(arrayListOf())
        photoView.adapter = adapter
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(HomeViewModel::class.java)
        Log.i("HomeACtivity", "Called ViewModelProviders.of")
    }

    private fun setupObservers() {
        viewModel.getUsers().observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        photoView.visibility = View.VISIBLE
                        //progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        photoView.visibility = View.VISIBLE
                        //progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        //progressBar.visibility = View.VISIBLE
                        photoView.visibility = View.GONE
                    }
                }
            }
        })
    }

    private fun retrieveList(users: PhotoDataModel) {
        adapter.apply {
            addUsers(users.hits)
            notifyDataSetChanged()
        }
    }

}
