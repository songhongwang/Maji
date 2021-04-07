package com.maji.mvvm.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient private constructor() {

    companion object {
        private const val endPoint = "https://api.github.com"

        val service: ApiService by lazy {
            val retrofit: Retrofit = Retrofit.Builder().baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .build()
            retrofit.create(ApiService::class.java)
        }
    }
}