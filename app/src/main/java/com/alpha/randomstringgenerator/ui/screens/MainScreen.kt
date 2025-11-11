package com.alpha.randomstringgenerator.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.alpha.randomstringgenerator.data.model.RandomString
import com.alpha.randomstringgenerator.ui.viewmodel.MainViewModel
import com.alpha.randomstringgenerator.utils.formatCreatedDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    var input by remember { mutableStateOf("") }
    var inputError by remember { mutableStateOf<String?>(null) }

    val strings = viewModel.stringList
    val isLoading = viewModel.isLoading.value
    val error = viewModel.errorMessage.value

    Scaffold(
        topBar = { TopAppBar(title = { Text("Random String Generator") }) },
        floatingActionButton = {
            if (strings.isNotEmpty()) {
                FloatingActionButton(onClick = { viewModel.deleteAll() }) {
                    Text("Clear")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = input,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        input = newValue
                        inputError = null
                    } else {
                        inputError = "Please enter only numbers"
                    }
                },
                label = { Text("Enter max length") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = inputError != null
            )

            // ✅ Validation error message
            inputError?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(top = 4.dp, start = 4.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    val len = input.toIntOrNull()
                    if (len != null && len > 0) {
                        viewModel.generateRandomString(len)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = input.isNotEmpty() && inputError == null
            ) {
                Text("Generate")
            }

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            error?.let {
                Text(
                    text = "Error: $it",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(strings) { item ->
                    StringItem(item = item, onDelete = { viewModel.deleteItem(item) })
                }
            }
        }
    }
}

@Composable
fun StringItem(item: RandomString, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("Value: ${item.value}", fontWeight = FontWeight.Bold)
            Text("Length: ${item.length}")
            Text("Created: ${formatCreatedDate(item.created)}") // ✅ formatted time
            Spacer(Modifier.height(4.dp))
            Button(onClick = onDelete, modifier = Modifier.align(Alignment.End)) {
                Text("Delete")
            }
        }
    }
}
