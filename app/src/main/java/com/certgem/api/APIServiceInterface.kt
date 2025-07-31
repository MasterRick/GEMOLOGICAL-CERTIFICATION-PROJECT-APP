package com.certgem.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIServiceInterface {
    companion object {
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

    @GET("search.json?key=&lang=pt_br")
    fun search(@Query("q") query: String): Call<List<API>?>
}