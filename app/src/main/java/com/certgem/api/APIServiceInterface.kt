package com.certgem.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServiceInterface {
    companion object {
        const val BASE_URL = "http://10.0.2.2:8080/"
    }

    @POST("usuario/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("usuario/sendEmailCodeGemologo")
    fun register(@Body request: CadastroRequest): Call<LoginResponse>

    @GET("certificado/findAll/{userId}")
    fun getAllCertificates(@Path("userId") userId: Long): Call<List<CertificateResponse>>
}