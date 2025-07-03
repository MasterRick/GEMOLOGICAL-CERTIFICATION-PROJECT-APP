package com.certgem.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Início")
    object History : BottomNavItem("history", Icons.Default.Search, "Histórico")
    object Find : BottomNavItem("find_gemologists", Icons.Default.LocationOn, "Encontrar")
}