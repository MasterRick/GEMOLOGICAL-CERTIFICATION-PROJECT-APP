package com.certgem.viewmodel

import androidx.lifecycle.ViewModel
import com.certgem.model.Certificate
import com.certgem.model.Gemologist
import com.certgem.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Date

class MainViewModel : ViewModel() {

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
        // Carga de dados iniciais para simulação
        loadInitialData()
    }

    fun login(email: String, pass: String): Boolean {
        // Lógica de login simulada
        if (email.isNotBlank() && pass.isNotBlank()) {
            _currentUser.value = User(id = "user01", name = "Usuário", email = email)
            return true
        }
        return false
    }

    fun register(name: String, email: String, pass: String): Boolean {
        // Lógica de registro simulada
        if (name.isNotBlank() && email.isNotBlank() && pass.isNotBlank()) {
            // Em um app real, você criaria o usuário no backend aqui.
            // Para o protótipo, vamos apenas logar o novo usuário.
            _currentUser.value = User(id = "user02", name = name, email = email)
            return true
        }
        return false
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