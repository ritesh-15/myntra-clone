package com.example.myntra.presentation.link_create_account_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.Poppins

@Composable
fun LinkCreateAccountScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {},
                title = {},
                backgroundColor = Color.White,
                elevation = 0.dp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(22.dp)
        ) {

            Text(text = "Already have an account?",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Your email address can be used on one myntra account only",
                fontSize = 12.sp,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    navController.navigate(Screen.LoginScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                contentPadding = PaddingValues(vertical = 12.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black.copy(0.4f))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Login With Email".uppercase(), fontFamily = Poppins)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Divider()
                Text(text = "OR",
                    fontFamily = Poppins,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(start = 4.dp, end = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))


            Text(text = "New to Myntra?",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate(Screen.CompleteSignUpScreen.route) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                ),
                contentPadding = PaddingValues(vertical = 12.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black.copy(0.4f))
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(imageVector = Icons.Outlined.Person, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Create a new account".uppercase(), fontFamily = Poppins)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun LinkCreateAccountScreenPreview() {
    LinkCreateAccountScreen(rememberNavController())
}