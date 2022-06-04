package com.example.myntra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.presentation.home_screen.HomeScreen
import com.example.myntra.ui.theme.MyntraTheme

class MainActivity : ComponentActivity() {
    
    private lateinit var navHostController: NavHostController
    
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
