package com.rahi.donut.data.network

import com.rahi.donut.data.model.DonutListModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiCallInterface {
    @GET("0ba63b71-bb15-434e-9da3-98435dcb346d")
    fun getAllDonut(): Call<List<DonutListModel>>
}