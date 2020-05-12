package com.apps.pixabayapp.ui.details.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.api.ApiHelper
import com.apps.pixabayapp.data.api.RetrofitBuilder
import com.apps.pixabayapp.data.model.PhotoDataModel
import com.apps.pixabayapp.ui.base.ViewModelFactory
import com.apps.pixabayapp.ui.home.adapter.PhotoAdapter
import com.apps.pixabayapp.ui.details.viewmodel.DetailsViewModel
import com.apps.pixabayapp.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        loadUi()
    }

    private fun loadUi() {

    }

}
