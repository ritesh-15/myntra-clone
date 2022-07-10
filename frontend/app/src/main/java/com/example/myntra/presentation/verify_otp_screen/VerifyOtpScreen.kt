package com.example.myntra.presentation.verify_otp_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
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
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary
import com.example.myntra.ui.theme.red

@Composable
fun VerifyOtpScreen(
    navController: NavController,
    email: String,
    hash: String,
    viewModel: VerifyOtpViewModel = hiltViewModel(),
) {
    // state

    val context = LocalContext.current

    val otp = remember {
        mutableStateOf("")
    }

    // view model

    val state = viewModel.state.value

    if (state.data != null) {
        LaunchedEffect(Unit) {
            if (state.data.user.isActive){
                navController.navigate(Screen.Home.passUser(state.data.user.name)) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            }else{
                navController.navigate(Screen.CompleteSignUpScreen.route) {
                    popUpTo(Screen.CompleteSignUpScreen.route) {
                        inclusive = true
                    }
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

            Text(text = "Verify with OTP",
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Send vai Email to $email",
                fontSize = 12.sp,
                fontFamily = Poppins
            )

            Spacer(modifier = Modifier.height(16.dp))
            // input
            OutlinedTextField(value = otp.value, onValueChange = {
                otp.value = it
            },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "One time password", fontFamily = Poppins)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    cursorColor = primary,
                    focusedBorderColor = primary,
                    focusedLabelColor = primary
                )
            )

            if (state.error != null) {
                Text(text = state.error, color = red,
                    modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // button
            Button(
                onClick = {
                    viewModel.verifyOtp(email = email, hash = hash, otp = otp.value.toInt())
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

                Text(text = "Verify", fontFamily = Poppins)
            }


            Spacer(modifier = Modifier.height(16.dp))

            Text(text = buildAnnotatedString {
                append("Log in using ")
                withStyle(style = SpanStyle(color = primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold)) {
                    append("Password")
                }
            }, fontFamily = Poppins, fontSize = 12.sp, modifier = Modifier.clickable {
                navController.navigate(route = Screen.LoginScreen.route)
            })

        }

    }
}

@Preview(showBackground = true)
@Composable
fun VerifyOtpScreenPreview() {
    VerifyOtpScreen(rememberNavController(), "", "")
}