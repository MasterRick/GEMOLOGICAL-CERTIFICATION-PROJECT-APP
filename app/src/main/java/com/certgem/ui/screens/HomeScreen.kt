package com.certgem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.certgem.ui.nav.BottomNavBar
import com.certgem.ui.nav.BottomNavItem
import com.certgem.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {
    val currentUser by viewModel.currentUser.collectAsState()
    val certificates by viewModel.certificates.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Bem-vindo(a), ${currentUser?.name ?: ""}") },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Seção de Botões de Ação
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { navController.navigate("create_certificate") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Criar Novo Certificado")
                    }
                    OutlinedButton(
                        onClick = { navController.navigate(BottomNavItem.History.route) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Histórico de Certificados")
                    }
                    OutlinedButton(
                        onClick = { navController.navigate(BottomNavItem.Find.route) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                        Text("Encontrar Gemólogos")
                    }
                }
            }

            // Seção de Últimos Certificados
            item {
                Text(
                    "Últimos Certificados Visualizados",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 24.dp, bottom = 8.dp)
                )
            }

            items(certificates.take(5)) { certificate -> // Mostra apenas os 5 mais recentes
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