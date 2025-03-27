package com.example.weight_converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weight_converter.ui.theme.Weight_converterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Weight_converterTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    WeightConverterApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightConverterApp() {
    var weight by remember { mutableStateOf("") }
    var unit by remember { mutableStateOf("KG") }
    var result by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    fun calculateResult() {
        val value = weight.toDoubleOrNull()
        if (value != null) {
            result = if (unit == "KG") "%.2f Libras".format(value * 2.20462) else "%.2f KG".format(value / 2.20462)
        }
    }

    Scaffold(
        topBar = {
            Surface(shadowElevation = 10.dp) {
                TopAppBar(
                    title = {
                        Text(
                            "Conversor de Peso",
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color(0xFF6200EE),
                        titleContentColor = Color.White,
                    ),
                )
            }
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                OutlinedTextField(
                    value = unit,
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    label = {
                        Text(
                            "Unidade",
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    }
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                "KG",
                            )
                       },
                        onClick = {
                            unit = "KG"
                            expanded = false
                            calculateResult()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            Text(
                                "Libras",
                            )
                       },
                        onClick = {
                            unit = "Libras"
                            expanded = false
                            calculateResult()
                        }
                    )
                }
            }

            OutlinedTextField(
                value = weight,
                onValueChange = {
                    weight = it.filter { c -> c.isDigit() || c == '.' }
                    calculateResult()
                },
                label = {
                    Text(
                        "Peso em $unit",
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            if (result.isNotEmpty()) {
                Text(
                    "Convertido: $result",
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Weight_converterTheme {
        WeightConverterApp()
    }
}
