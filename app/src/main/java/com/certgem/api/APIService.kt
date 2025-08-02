package com.certgem.api

import android.util.Log
import retrofit2.*

class APIService {

    private var api: APIServiceInterface
    public var token = "";

    init {

        api = RetrofitClient.getClient(token)
    }

    fun login(username: String, password: String, onResult: (LoginResponse?) -> Unit) {
        val request = LoginRequest(username, password)

        api.login(request).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    onResult(loginResponse)
                    api = RetrofitClient.getClient(loginResponse?.token )
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
                    api = RetrofitClient.getClient(registerResponse?.token )
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

    fun getCertificates(
        userId: Long,
        onResult: (List<CertificateResponse>?) -> Unit
    ) {
        api.getAllCertificates(userId).enqueue(object : Callback<List<CertificateResponse>> {
            override fun onResponse(
                call: Call<List<CertificateResponse>>,
                response: Response<List<CertificateResponse>>
            ) {
                if (response.isSuccessful) {
                    val certificates = response.body()
                    onResult(certificates)
                    Log.d("Certificates", "Certificados recebidos com sucesso! Quantidade: ${certificates?.size}")
                } else {
                    onResult(null)
                    Log.e("Certificates", "Erro ao buscar certificados: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<CertificateResponse>>, t: Throwable) {
                onResult(null)
                Log.e("Certificates", "Falha na requisição: ${t.message}")
            }
        })
    }

}
