package com.saha.lifeplustenicaltest.view.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityMainBinding
import com.saha.lifeplustenicaltest.utils.LoadingDialog
import com.saha.lifeplustenicaltest.utils.edxtensions.trackTypingState
import com.saha.lifeplustenicaltest.utils.handleScreenState
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper
import com.saha.lifeplustenicaltest.view.activity.profile.ProfileActivity
import com.saha.lifeplustenicaltest.view.activity.searchDetails.SearchItemDetails
import com.saha.lifeplustenicaltest.view.components.adapters.SearchResultAdapter

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var loadingDialog: LoadingDialog

    private lateinit var searchResultAdapter: SearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        componentInit()
        listener()
        clickListeners()
        viewModelObservers()
        getIntentData()
        initData()

    }

    private fun componentInit() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application, RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[MainViewModel::class.java]
        loadingDialog = LoadingDialog(this)

        searchResultAdapter = SearchResultAdapter(this@MainActivity, mutableListOf()) { data, position ->
            startActivity(
                Intent(this@MainActivity, SearchItemDetails::class.java).putExtras(
                    bundleOf("detailsData" to data)
                )
            )
        }

        binding.rvSearchResult.apply {
            adapter = searchResultAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    private fun listener() {

        binding.search.searchEditText.trackTypingState(
            startTyping = {

            },
            typingComplete = {
                viewModel.searchShows(binding.search.searchEditText.text.toString())
            }
        )
    }

    private fun clickListeners() {
        binding.ivProfile.setOnClickListener {
            startActivity(
                Intent(
                    this, ProfileActivity::class.java
                )
            )
        }

    }

    private fun viewModelObservers() {

        viewModel.searchResult.observe(this) {
            handleScreenState(it, loadingDialog, successAction = { data, msg ->
                loadingDialog.hide()

                if (data.isEmpty()){
                    //show empty view
                    searchResultAdapter.setData(mutableListOf())
                }else{
                    searchResultAdapter.setData(data)
                }
            })
        }

    }

    private fun getIntentData() {

    }

    private fun initData() {

        //viewModel.searchShows("girls")
    }
}