package com.example.myntra.presentation.login_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.common.Screen
import com.example.myntra.common.utils.TokenHandler
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.primary
import com.example.myntra.ui.theme.red

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {

    val state = viewModel.state.value
    val context = LocalContext.current

    if (state.error != null) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    if (state.data != null) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.Home.passUser(state.data.user.name)) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }

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

            Text(text = "Login to your account",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(16.dp))
            // input
            OutlinedTextField(value = viewModel.email.value, onValueChange = {
                viewModel.onEmailChange(it)
            },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Email Address", fontFamily = Poppins)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = primary,
                    focusedBorderColor = primary,
                    focusedLabelColor = primary
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = viewModel.password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Password", fontFamily = Poppins)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Password
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = primary,
                    focusedBorderColor = primary,
                    focusedLabelColor = primary
                ),
                trailingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null)
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.login()
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primary,
                    contentColor = Color.White
                ),
                contentPadding = PaddingValues(vertical = 12.dp),
                enabled = !state.loading
            ) {
                if (state.loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(20.dp)
                            .width(20.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                }

                Text(text = "Login".uppercase(), fontFamily = Poppins)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = buildAnnotatedString {
                append("Forgot your password? ")
                withStyle(style = SpanStyle(color = primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold)) {
                    append("Reset here")
                }
            }, fontFamily = Poppins, fontSize = 12.sp, modifier = Modifier.clickable {
                navController.navigate(route = Screen.ResetPasswordScreen.route)
            })

        }

    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(rememberNavController())
}