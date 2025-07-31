package com.certgem.api

import android.util.Log
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class APIService {

    private var api: APIServiceInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(APIServiceInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(APIServiceInterface::class.java)
    }

    fun login(username: String, password: String, onResult: (LoginResponse?) -> Unit) {
        val request = LoginRequest(username, password)

        api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    onResult(loginResponse)
                    Log.d("Login", "Sucesso! Token: ${loginResponse?.token}")
                } else {
                    onResult(null)
                    Log.e("Login", "Erro de resposta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(null)
                Log.e("Login", "Falha na requisição: ${t.message}")
            }
        })
    }

    fun register(
        username: String,
        email: String,
        password: String,
        onResult: (LoginResponse?) -> Unit
    ) {
        val request = CadastroRequest(username, email, password)

        api.register(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    onResult(registerResponse)
                    Log.d("Register", "Cadastro realizado com sucesso! ID: ${registerResponse?.userId}")
                } else {
                    onResult(null)
                    Log.e("Register", "Erro no cadastro: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(null)
                Log.e("Register", "Falha na requisição: ${t.message}")
            }
        })
    }
}
