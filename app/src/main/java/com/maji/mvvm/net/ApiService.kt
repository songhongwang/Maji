package com.maji.mvvm.net

import com.maji.mvvm.model.Subject
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/")
    fun getList(): RespLiveData<Subject?>
}