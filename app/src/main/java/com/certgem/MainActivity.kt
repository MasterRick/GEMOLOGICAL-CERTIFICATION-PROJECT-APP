package com.certgem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.certgem.api.APIService
import com.certgem.ui.nav.MainNavHost
import com.certgem.ui.theme.CertGemTheme
import com.certgem.viewmodel.MainViewModel
import com.certgem.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val apiService = remember { APIService() }
            val viewModel : MainViewModel = viewModel(
                factory = MainViewModelFactory(apiService)
            )

            CertGemTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MainNavHost(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}