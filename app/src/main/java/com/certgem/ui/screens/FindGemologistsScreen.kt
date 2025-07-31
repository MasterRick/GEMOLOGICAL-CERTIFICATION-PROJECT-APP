package com.certgem.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.certgem.viewmodel.MainViewModel
import com.certgem.model.Gemologist
import com.certgem.ui.nav.BottomNavBar
import com.certgem.ui.nav.BottomNavItem
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

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
            gemologists = gemologists,
            viewModel = viewModel
        )
    }
}

@Composable
private fun TabScreen(modifier: Modifier = Modifier, gemologists: List<Gemologist>, viewModel: MainViewModel) {
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
            0 -> MapContent(modifier = modifier, viewModel = viewModel)
            1 -> ListContent(gemologists = gemologists)
        }
    }
}

@Composable
private fun MapContent(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    val recife = LatLng(-8.05, -34.9);
    val caruaru = LatLng(-8.27, -35.98);
    val joaopessoa = LatLng(-7.12, -34.84);
    val camPosState = rememberCameraPositionState ();

    val context = LocalContext.current
    val hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED
        )
    }



    GoogleMap (modifier = Modifier.fillMaxSize(), onMapClick = {
        /*viewModel.add(it)*/ }
        ,cameraPositionState = camPosState, properties = MapProperties(isMyLocationEnabled = hasLocationPermission),
        uiSettings = MapUiSettings(myLocationButtonEnabled = true)) {
        /*viewModel.cities.forEach {
            if (it.location != null) {
                Marker( state = MarkerState(position = it.location),
                    title = it.name, snippet = "${it.location}")
            }
        }*/

        Marker(
            state = MarkerState(position = recife),
            title = "Recife",
            snippet = "Marcador em Recife",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_BLUE)
        )
        Marker(
            state = MarkerState(position = caruaru),
            title = "Recife",
            snippet = "Marcador em Caruaru",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED)
        )
        Marker(
            state = MarkerState(position = joaopessoa),
            title = "Recife",
            snippet = "Marcador em João Pessoa",
            icon = BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_MAGENTA)
        )

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