package com.certgem.ui.nav

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.certgem.viewmodel.MainViewModel
import com.certgem.ui.screens.*

@Composable
fun MainNavHost(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, viewModel = viewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.Home.route) {
            viewModel.loadInitialData()
            HomeScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.History.route) {
            HistoryScreen(navController = navController, viewModel = viewModel)
        }
        composable(BottomNavItem.Find.route) {
            FindGemologistsScreen(navController = navController, viewModel = viewModel)
        }
        composable("create_certificate") {
            CreateCertificateScreen(navController = navController, viewModel = viewModel)
        }
    }
}