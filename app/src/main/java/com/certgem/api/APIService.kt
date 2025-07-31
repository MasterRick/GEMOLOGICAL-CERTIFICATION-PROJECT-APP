package com.certgem.api

import android.util.Log
import com.certgem.api.API
import com.certgem.api.APIServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    private var weatherAPI: APIServiceInterface
    init {
        val retrofitAPI = Retrofit.Builder().baseUrl(APIServiceInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
        weatherAPI = retrofitAPI.create(APIServiceInterface::class.java)
    }
    fun getName(lat: Double, lng: Double, onResponse : (String?) -> Unit ) {
        search("$lat,$lng") { loc -> onResponse (loc?.name) }
    }
    fun     getLocation(name: String, onResponse: (lat:Double?, long:Double?) -> Unit) {
        search(name) { loc -> onResponse (loc?.lat, loc?.lon) }
    }
    private fun search(query: String, onResponse : (API?) -> Unit) {
        val call: Call<List<API>?> = weatherAPI.search(query)
        call.enqueue(object : Callback<List<API>?> {
            override fun onResponse(call: Call<List<API>?>,
                                    response: Response<List<API>?>
            ) {
                onResponse(response.body()?.let {if (it.isNotEmpty()) it[0] else null})
            }
            override fun onFailure(call: Call<List<API>?>, t: Throwable) {
                Log.w("WeatherApp WARNING", "" + t.message)
                onResponse(null)
            }
        })
    }
}