package com.saha.lifeplustenicaltest.view.activity.searchDetails

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivitySearchItemDetailsBinding
import com.saha.lifeplustenicaltest.utils.edxtensions.getCompatSerializableExtra
import com.saha.lifeplustenicaltest.utils.edxtensions.loadHtml
import com.saha.lifeplustenicaltest.utils.edxtensions.loadImage
import com.saha.lifeplustenicaltest.utils.helpers.AppHelper
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper

class SearchItemDetails : AppCompatActivity() {
    private lateinit var binding: ActivitySearchItemDetailsBinding
    private lateinit var viewModel: ScheduleRideDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_item_details)

        componentInit()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()
    }

    private fun componentInit() {
        binding = ActivitySearchItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application, RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[ScheduleRideDetailsViewModel::class.java]

    }

    private fun clickListeners() {

        binding.appBar.backButton.backButton.setOnClickListener{
            onBackPressedDispatcher.onBackPressed()
        }

        binding.ivImdb.setOnClickListener {
            viewModel.intentData?.show?.externals?.imdb?.let {
                AppHelper.openBrowserWithURL(this, "https://www.imdb.com/title/$it")
            }
        }


        binding.btnWatchNow.btn.setOnClickListener {
            viewModel.intentData?.show?.url?.let { url ->
                AppHelper.openBrowserWithURL(this, url)
            }
        }

    }

    private fun viewModelObservers() {

    }

    private fun getIntentData() {
        intent.extras?.let { extras ->
            extras.getCompatSerializableExtra<ResponseSearchItem>("detailsData")?.let {

                viewModel.intentData = it
                bindData(it)

            }

        }
    }

    private fun initData() {

    }

    private fun bindData(data: ResponseSearchItem) {
        data.show?.let {


            binding.tvTitle.text = it.name
            it.summary?.let { it1 -> binding.tvDescription.loadHtml(it1) }

            if (it.rating?.average != null){
                binding.tvRating.text = it.rating.average.toString()
                binding.tvRating.visibility = View.VISIBLE
            }else{
                binding.tvRating.visibility = View.GONE
            }


            it.image?.original?.let { it1 -> binding.ivItemImage.loadImage(it1, R.drawable.image_placeholder_icon) }

            if (it.externals?.imdb != null) {
                binding.ivImdb.visibility = View.VISIBLE
            } else {
                binding.ivImdb.visibility = View.GONE
            }
        }

    }
}