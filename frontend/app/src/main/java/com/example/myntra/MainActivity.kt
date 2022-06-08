package com.example.myntra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.common.Navigation
import com.example.myntra.ui.theme.MyntraTheme

class MainActivity : ComponentActivity() {

    private lateinit var navHostController:NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyntraTheme {
               navHostController = rememberNavController()
                Navigation(navController = navHostController)
            }
        }
    }
}
