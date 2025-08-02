package com.certgem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.certgem.ui.nav.BottomNavBar
import com.certgem.ui.nav.BottomNavItem
import com.certgem.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController, viewModel: MainViewModel) {
    val certificates by viewModel.certificates.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    val filteredCertificates = certificates.filter {
        it.numeroCertificado.contains(searchQuery, ignoreCase = true) ||
                it.dataCriacao.toString().contains(searchQuery, ignoreCase = true) ||
                it.observacoes.toString().contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Histórico") },
                actions = {
                    IconButton(onClick = {
                        viewModel.logout()
                        navController.navigate("login") {
                            popUpTo(BottomNavItem.Home.route) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sair")
                    }
                }
            )
        },
        bottomBar = { BottomNavBar(navController = navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar por nome, nº ou proprietário") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Pesquisar") },
                modifier = Modifier.fillMaxWidth()
            )
            // Placeholder para o filtro de calendário
            OutlinedButton(
                onClick = { /* Lógica do calendário aqui */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Filtrar por data")
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filteredCertificates) { certificate ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp)) {
                            Text("Nº: ${certificate.numeroCertificado}", style = MaterialTheme.typography.bodySmall)
                            Text("Observações: ${certificate.observacoes}", style = MaterialTheme.typography.bodyMedium)
                            Text("Data: ${certificate.dataCriacao}", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}