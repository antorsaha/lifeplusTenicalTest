package com.saha.lifeplustenicaltest.view.activity.searchDetails

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivitySearchItemDetailsBinding
import com.saha.lifeplustenicaltest.databinding.ActivitySplashBinding
import com.saha.lifeplustenicaltest.utils.edxtensions.getCompatSerializableExtra
import com.saha.lifeplustenicaltest.utils.edxtensions.loadImage
import com.saha.lifeplustenicaltest.utils.helpers.AppHelper
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper
import com.saha.lifeplustenicaltest.view.activity.profile.ProfileViewModel

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
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[ScheduleRideDetailsViewModel::class.java]

    }

    private fun clickListeners() {

        viewModel.intentData?.show?.externals?.imdb?.let {
            binding.ivImdb.setOnClickListener{
                AppHelper.openBrowserWithURL(this, "https://www.imdb.com/title/$it")
            }
        }

        viewModel.intentData?.show?.url?.let { url ->
            binding.btnWatchNow.btn.setOnClickListener{
                AppHelper.openBrowserWithURL(this, url)
            }
        }

    }

    private fun viewModelObservers() {

    }

    private fun getIntentData() {
        intent.extras?.let { extras ->
            extras.getCompatSerializableExtra<ResponseSearchItem>("detailsData")
                ?.let {

                    bindData(it)

                }

        }
    }

    private fun initData() {

    }

    private fun bindData(data: ResponseSearchItem){
        data.show?.let {


            binding.tvTitle.text = it.name
            binding.tvDescription.text = it.summary
            binding.tvRating.text = it.rating?.average.toString()

            it.image?.original?.let { it1 -> binding.ivItemImage.loadImage(it1) }

            if (it.externals?.imdb != null){
                binding.ivImdb.visibility = View.VISIBLE
            }else{
                binding.ivImdb.visibility = View.GONE
            }
        }

    }
}