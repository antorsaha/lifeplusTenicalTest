package com.saha.lifeplustenicaltest.data.network

import com.google.gson.GsonBuilder
import com.saha.lifeplustenicaltest.BuildConfig
import com.saha.lifeplustenicaltest.data.model.ResponseSearchItem
import com.saha.lifeplustenicaltest.utils.ApiConstants
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MyApi {


    @GET("search/shows")
    suspend fun searchShow(
        @Query("q") query: String
    ): Response<MutableList<ResponseSearchItem>>


    companion object {

        private var myApiInstance: MyApi? = null
        private val LOCK = Any()

        operator fun invoke() = myApiInstance ?: synchronized(LOCK) {
            myApiInstance ?: createClient().also {
                myApiInstance = it
            }
        }

        private fun createClient(): MyApi {

            val interceptor = run {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.apply {
                    if (BuildConfig.DEBUG) {
                        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                    }


                }
            }


            //Log.d(TAG, "createClient: token " + MyApplication.getToken())
            val okHttpClient: OkHttpClient = OkHttpClient.Builder().readTimeout(2, TimeUnit.MINUTES)
                .connectTimeout(2, TimeUnit.MINUTES).callTimeout(2, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true).addInterceptor(interceptor)

                .addInterceptor { chain ->
                    val original: Request = chain.request()
                    val requestBuilder: Request.Builder =
                        original.newBuilder().addHeader("Connection", "keep-alive")
                            .addHeader("Accept", "*/*")
                            .addHeader("Content-Type", "application/json")

                    /*if (shouldAddAuthHeaders){
                        requestBuilder.addHeader("Authorization", "Bearer ${AppHelper.getAccessToken()}")
                    }*/

                    requestBuilder.method(original.method, original.body)
                    val request: Request = requestBuilder.build()

                    chain.proceed(request)
                }

                .build()

            val gsonBuilder = GsonBuilder()
            gsonBuilder.setLenient()
            val gson = gsonBuilder.create()

            return Retrofit.Builder().baseUrl(ApiConstants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
                .create(MyApi::class.java)
        }


    }
}