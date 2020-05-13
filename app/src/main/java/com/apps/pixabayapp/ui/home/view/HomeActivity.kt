package com.apps.pixabayapp.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.pixabayapp.MyApplication
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.api.ApiService
import com.apps.pixabayapp.data.model.DetailsData
import com.apps.pixabayapp.data.model.ListOfPhotos
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.apps.pixabayapp.ui.base.ViewModelFactory
import com.apps.pixabayapp.ui.details.view.DetailsActivity
import com.apps.pixabayapp.ui.home.adapter.PhotoAdapter
import com.apps.pixabayapp.ui.home.viewmodel.HomeViewModel
import com.apps.pixabayapp.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    PhotoAdapter.OnItemClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: PhotoAdapter
    private var isLoading = false
    private lateinit var query: String

    private var page: Int = 1

    @Inject
    lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as MyApplication).getNetComponent().inject(this)
        setupViewModel()
        setupUI()
        //setupObservers("apple")
        setupSearch()
    }

    private fun setupSearch() {
        search.maxWidth = Integer.MAX_VALUE
        search.setOnQueryTextListener(this)
        search.setQuery("apple", true)
        query = search.query.toString()
    }

    private fun setupUI() {

        adapter = PhotoAdapter(arrayListOf(), this)

        photoView.layoutManager = GridLayoutManager(this, 2)
        photoView.adapter = adapter
        photoView.isNestedScrollingEnabled = false


        photoView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount: Int? =
                    layoutManager?.findLastCompletelyVisibleItemPosition() + 1
                val totalItemCount: Int? = layoutManager?.itemCount

                if (!isLoading && visibleItemCount == totalItemCount) {
                    //Load more data
                    isLoading = true
                    page += 1
                    loaderBar.visibility = View.VISIBLE
                    setupObservers(query, page)
                }
            }
        })

    }

    private fun setupViewModel() {
        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(ApiHelper(apiService)))
                .get(HomeViewModel::class.java)
        Log.i("HomeACtivity", "Called ViewModelProviders.of")
    }

    private fun setupObservers(query: String, page: Int) {
        viewModel.getUsers(query, page).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        photoView.visibility = View.VISIBLE
                        isLoading = false
                        loaderBar.visibility = View.GONE
                        //progressBar.visibility = View.GONE
                        resource.data?.let { users -> retrieveList(users) }
                    }
                    Status.ERROR -> {
                        photoView.visibility = View.VISIBLE
                        isLoading = false
                        loaderBar.visibility = View.GONE
                        //progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                        Log.i("TAG", it.message)
                    }
                    Status.LOADING -> {
                        //progressBar.visibility = View.VISIBLE
                        //photoView.visibility = View.GONE
                    }
                    Status.CONTENT_ERROR -> {
                        photoView.visibility = View.VISIBLE
                        isLoading = false
                        loaderBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
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

    private fun clearList() {
        adapter.apply {
            clearList()
            notifyDataSetChanged()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
            clearList()
            page = 1
            this.query = query
            setupObservers(query!!, page)
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onItemClick(item: ListOfPhotos) {
        val detailsData: DetailsData = DetailsData(
            item.comments,
            item.webformatURL,
            item.views,
            item.favorites,
            item.downloads,
            item.likes,
            item.previewURL
        )
        val intent: Intent = Intent(this, DetailsActivity::class.java)
        startActivity(intent.putExtra("details", detailsData))
    }

}
