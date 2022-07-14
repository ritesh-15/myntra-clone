package com.example.myntra.presentation.address_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun AddressesScreen(navController: NavController) {

}

@Preview(showBackground = true)
@Composable
fun AddressesScreenPreview() {
    AddressesScreen(navController = rememberNavController())
}