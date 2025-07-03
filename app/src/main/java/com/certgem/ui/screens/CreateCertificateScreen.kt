package com.certgem.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.certgem.viewmodel.MainViewModel
import com.certgem.model.Certificate
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCertificateScreen(navController: NavController, viewModel: MainViewModel) {
    var gemName by remember { mutableStateOf("") }
    var item by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var measurements by remember { mutableStateOf("") }
    var shape by remember { mutableStateOf("") }
    var color by remember { mutableStateOf("") }
    var transparency by remember { mutableStateOf("") }
    var clarity by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var variety by remember { mutableStateOf("") }
    var treatment by remember { mutableStateOf("") }
    var origin by remember { mutableStateOf("") }
    var comments by remember { mutableStateOf("") }
    var observations by remember { mutableStateOf("") }
    var owner by remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Certificado") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Seção 1
            Text("Especificações da Gema", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(value = gemName, onValueChange = { gemName = it }, label = { Text("Nome") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = item, onValueChange = { item = it }, label = { Text("Item (Ex: Anel, Colar)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = weight, onValueChange = { weight = it }, label = { Text("Peso (Ex: 2.5 ct)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = measurements, onValueChange = { measurements = it }, label = { Text("Medidas (mm)") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = shape, onValueChange = { shape = it }, label = { Text("Forma") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = color, onValueChange = { color = it }, label = { Text("Cor") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = transparency, onValueChange = { transparency = it }, label = { Text("Transparência") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = clarity, onValueChange = { clarity = it }, label = { Text("Clareza") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            // Seção 2
            Text("Resultado da Avaliação", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(value = species, onValueChange = { species = it }, label = { Text("Espécie") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = variety, onValueChange = { variety = it }, label = { Text("Variedade") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = treatment, onValueChange = { treatment = it }, label = { Text("Tratamento") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = origin, onValueChange = { origin = it }, label = { Text("Origem") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            // Seção 3
            Text("Informações Adicionais", style = MaterialTheme.typography.titleLarge)
            OutlinedTextField(value = comments, onValueChange = { comments = it }, label = { Text("Comentários") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = observations, onValueChange = { observations = it }, label = { Text("Observações") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = owner, onValueChange = { owner = it }, label = { Text("Proprietário") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val newCert = Certificate(
                        id = UUID.randomUUID().toString(),
                        number = "CG-2024-${(100..999).random()}",
                        emissionDate = Date(),
                        gemName = gemName, item = item, weight = weight, measurements = measurements,
                        shape = shape, color = color, transparency = transparency, clarity = clarity,
                        species = species, variety = variety, treatment = treatment, origin = origin,
                        comments = comments, observations = observations, owner = owner
                    )
                    viewModel.createCertificate(newCert)
                    Toast.makeText(context, "Certificado criado!", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Text("Gerar Certificado")
            }
        }
    }
}