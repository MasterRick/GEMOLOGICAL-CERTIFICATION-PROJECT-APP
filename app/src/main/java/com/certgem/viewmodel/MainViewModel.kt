package com.certgem.viewmodel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.certgem.api.APIService
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
    private val _certificates = MutableStateFlow<List<Certificate>>(emptyList())
    val certificates = _certificates.asStateFlow()

    // Simula uma lista de gemólogos
    private val _gemologists = MutableStateFlow<List<Gemologist>>(emptyList())
    val gemologists = _gemologists.asStateFlow()

    init {
        loadInitialData()
    }

    fun login(email: String, pass: String, onResult: (Boolean) -> Unit) {
        if (email.isNotBlank() && pass.isNotBlank()) {
            service.login(email, pass) { success ->
                if (success != null) {
                    println(success.userId)
                    _currentUser.value = User(id = success.userId.toString(), name = email, email = email, token=success.token.toString())
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
                        id = success.userId.toString(),
                        name = email,
                        email = email,
                        token = success.token
                    )
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
        val currentList = _certificates.value.toMutableList()
        currentList.add(0, cert) // Adiciona no início da lista
        _certificates.value = currentList
    }

    private fun loadInitialData() {
        // Certificados de exemplo
        _certificates.value = listOf(
            Certificate("cert001", "CG-2023-001", Date(), "Diamante", "Anel de Noivado", "1.02 ct", "6.5 x 6.5 x 4.0 mm", "Brilhante", "Incolor (D)", "Transparente", "VVS1", "Diamante Natural", "Diamante", "Nenhum", "África do Sul", "Gema de alta qualidade.", "Nenhuma.", "Maria Silva"),
            Certificate("cert002", "CG-2023-002", Date(), "Esmeralda", "Pingente", "3.45 ct", "10.0 x 7.5 x 6.0 mm", "Octogonal", "Verde Intenso", "Transparente", "Inclusões Típicas", "Berilo", "Esmeralda", "Óleo", "Colômbia", "Cor excepcional.", "Fraturas preenchidas.", "João Costa"),
            Certificate("cert003", "CG-2023-003", Date(), "Safira", "Brincos", "2.10 ct (par)", "7.0 x 5.0 x 3.5 mm", "Oval", "Azul Royal", "Transparente", "VS", "Coríndon", "Safira", "Tratamento Térmico", "Sri Lanka", "Comentário genérico.", "Observação qualquer.", "Joana Andrade")
        )

        // Gemólogos de exemplo
        _gemologists.value = listOf(
            Gemologist("gem01", "Dr. Carlos Rocha", "Rua das Pedras, 123, São Paulo - SP", 2.5),
            Gemologist("gem02", "Mariana Alves", "Av. dos Diamantes, 45, Rio de Janeiro - RJ", 5.8),
            Gemologist("gem03", "Pedro Lima", "Praça do Ouro, 789, Belo Horizonte - MG", 10.2)
        ).sortedBy { it.distanceFromUser }
    }
}

class MainViewModelFactory(private val service : APIService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(service) as T;
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}