package com.certgem.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.certgem.viewmodel.MainViewModel
import com.certgem.model.Gemologist
import com.certgem.ui.nav.BottomNavBar
import com.certgem.ui.nav.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FindGemologistsScreen(navController: NavController, viewModel: MainViewModel) {
    val gemologists by viewModel.gemologists.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Encontrar Gemólogos") },
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
        // --- Estrutura principal com abas ---
        TabScreen(
            modifier = Modifier.padding(paddingValues),
            gemologists = gemologists
        )
    }
}

@Composable
private fun TabScreen(modifier: Modifier = Modifier, gemologists: List<Gemologist>) {
    // Estado para controlar o índice da aba selecionada (0 para Mapa, 1 para Lista)
    var tabIndex by remember { mutableStateOf(0) }

    // Lista com os títulos das abas
    val tabs = listOf("Mapa", "Lista")

    Column(modifier = modifier.fillMaxWidth()) {
        // O componente TabRow que exibe as abas
        TabRow(selectedTabIndex = tabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    text = { Text(text = title) }
                )
            }
        }

        // Exibe o conteúdo correspondente à aba selecionada
        when (tabIndex) {
            0 -> MapContent()
            1 -> ListContent(gemologists = gemologists)
        }
    }
}

// Composable para o conteúdo da aba "Mapa"
@Composable
private fun MapContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f) // Ocupa 80% da altura disponível
        ) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(
                    "[ Futura implementação do Mapa com API do Google ]\nExibirá os pins dos gemólogos próximos.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

// Composable para o conteúdo da aba "Lista"
@Composable
private fun ListContent(gemologists: List<Gemologist>) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                "Gemólogos ordenados por proximidade",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        items(gemologists) { gemologist ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(gemologist.name, style = MaterialTheme.typography.titleMedium)
                    Text(gemologist.address, style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "~${gemologist.distanceFromUser} km de distância",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}