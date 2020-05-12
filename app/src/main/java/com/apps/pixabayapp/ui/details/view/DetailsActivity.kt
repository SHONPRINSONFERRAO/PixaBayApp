package com.apps.pixabayapp.ui.details.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.apps.pixabayapp.MyApplication
import com.apps.pixabayapp.R
import com.apps.pixabayapp.data.model.DetailsData
import com.apps.pixabayapp.databinding.ActivityDetailsLayoutBinding
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details_layout.*


class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityDetailsLayoutBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_details_layout)

        val details = intent.getSerializableExtra("details") as DetailsData
        binding.data = details

        val url = if (details.url.isNullOrEmpty() || !MyApplication.hasNetwork()) {
            details.thumbnail
        } else {
            details.url
        }

        Glide.with(this)
            .load(url)
            .thumbnail(Glide.with(this).load(details.thumbnail))
            .placeholder(R.drawable.ic_placeholder_image)
            .error(R.drawable.ic_placeholder_image)
            .into(image)
    }

}
