package com.certgem.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.certgem.api.APIService
import com.certgem.api.CertificateResponse
import com.certgem.api.RetrofitClient
import com.certgem.model.Certificate
import com.certgem.model.Gemologist
import com.certgem.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class MainViewModel(private val service : APIService) : ViewModel() {



    // Simula um usuário logado
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    // Simula uma lista de certificados em memória
    private val _certificates = MutableStateFlow<List<CertificateResponse>>(emptyList())
    val certificates = _certificates.asStateFlow()

    // Simula uma lista de gemólogos
    private val _gemologists = MutableStateFlow<List<Gemologist>>(emptyList())
    val gemologists = _gemologists.asStateFlow()



    fun login(email: String, pass: String, onResult: (Boolean) -> Unit) {
        if (email.isNotBlank() && pass.isNotBlank()) {
            service.login(email, pass) { success ->
                if (success != null) {
                    println(success.userId)
                    _currentUser.value = User(
                        id = success.userId,
                        name = email,
                        email = email,
                        token = success.token
                    )

                    service.token = success.token;

                    onResult(true)
                } else {
                    onResult(false)
                }
            }
        } else {
            onResult(false)
        }
    }

    fun register(name: String, email: String, pass: String, onResult: (Boolean) -> Unit) {
        if (name.isNotBlank() && email.isNotBlank() && pass.isNotBlank()) {
            service.register(name, email, pass) { success ->
                if (success != null) {
                    println(success.userId)
                    _currentUser.value = User(
                        id = success.userId,
                        name = email,
                        email = email,
                        token = success.token
                    )
                    service.token = success.token;
                    onResult(true)
                } else {
                    onResult(false)
                }
            }
        } else {
            onResult(false)
        }
    }


    fun logout() {
        _currentUser.value = null
    }

    fun createCertificate(cert: Certificate) {
        /*val currentList = _certificates.value.toMutableList()
        currentList.add(0, cert) // Adiciona no início da lista
        _certificates.value = currentList*/
    }

    private fun getCertificates(userId: Long, onResult: (Boolean) -> Unit) {
        service.getCertificates(userId) { certificates ->
            if (certificates != null) {
                _certificates.value = certificates
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }


    fun loadInitialData() {
        val userId = _currentUser.value?.id;

        if (userId != null) {
            getCertificates(userId) { success ->
                if (success) {
                    // Após carregar certificados com sucesso, carregamos os gemólogos
                    _gemologists.value = listOf(
                        Gemologist(
                            "gem01",
                            "Dr. Carlos Rocha",
                            "Rua das Pedras, 123, São Paulo - SP",
                            2.5
                        ),
                        Gemologist(
                            "gem02",
                            "Mariana Alves",
                            "Av. dos Diamantes, 45, Rio de Janeiro - RJ",
                            5.8
                        ),
                        Gemologist(
                            "gem03",
                            "Pedro Lima",
                            "Praça do Ouro, 789, Belo Horizonte - MG",
                            10.2
                        )
                    ).sortedBy { it.distanceFromUser }
                } else {
                    Log.e("LoadData", "Falha ao carregar certificados")
                    // Mesmo em caso de erro, você pode optar por carregar os gemólogos ou mostrar uma mensagem de erro
                }
            }
        } else {
            Log.e("LoadData", "ID do usuário inválido")
        }
    }
}

    class MainViewModelFactory(private val service: APIService) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                return MainViewModel(service) as T;
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
