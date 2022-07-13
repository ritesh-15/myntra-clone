package com.example.myntra.presentation.refresh_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.primary

@Composable
fun RefreshScreen(
    navController: NavController,
    viewModel: RefreshViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value

    if (state.error != null) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.data != null) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.passUser(state.data.user.name)) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .width(75.dp)
                .height(75.dp))

        Spacer(modifier = Modifier.height(16.dp))

        CircularProgressIndicator(
            color = primary,
            strokeWidth = 2.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RefreshScreenPreview() {
    RefreshScreen(navController = rememberNavController())
}