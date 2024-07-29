package com.saha.lifeplustenicaltest.view.activity.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.saha.lifeplustenicaltest.MyApplication
import com.saha.lifeplustenicaltest.R
import com.saha.lifeplustenicaltest.data.model.User
import com.saha.lifeplustenicaltest.data.repo.RepositoryImpl
import com.saha.lifeplustenicaltest.databinding.ActivityMainBinding
import com.saha.lifeplustenicaltest.utils.helpers.ViewModelInstanceHelper

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel = ViewModelProvider(
            this, ViewModelInstanceHelper(
                this.application,
                RepositoryImpl((this.application as MyApplication).myApi)
            )
        )[MainViewModel::class.java]

        viewModel.saveUser(User(name = "antor saha"))

        binding.helloWorld.setOnClickListener{
            viewModel.getUser()
        }



        viewModel.userLiveData.observe(this){
            Log.d(TAG, "onCreate: ${it.name}")
        }
    }
}