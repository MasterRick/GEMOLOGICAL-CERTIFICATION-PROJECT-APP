package com.certgem.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithAuth = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(requestWithAuth)
    }
}

object RetrofitClient {

    fun getClient(token: String?): APIServiceInterface {
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(token?: ""))
            .build()

        return Retrofit.Builder()
            .baseUrl(APIServiceInterface.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIServiceInterface::class.java)
    }
}