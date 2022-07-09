package com.example.myntra.presentation.login_signup_screen

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.primary
import com.example.myntra.ui.theme.red

@Composable
fun LoginSignUpScreen(navController: NavController) {

    // state
    val email = remember {
        mutableStateOf("")
    }

    val viewModel: LoginSignupViewModel = hiltViewModel()
    val state = viewModel.state.value

    Log.d("loginSignUp", state.toString())

    if (state.data != null) {
       LaunchedEffect(Unit){
           navController.navigate(Screen.VerifyOtpScreen.
           passEmailAndHash(email = state.data.email,
               hash = state.data.hash))
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

            // image

            Image(painter = painterResource(id = R.drawable.ic_auth),
                contentDescription = null)

            Spacer(modifier = Modifier.height(16.dp))

            // login or signup text
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Bold
                )) {
                    append("Login ")
                }
                append("or")
                withStyle(style = SpanStyle(color = Color.Black,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Bold)) {
                    append(" Sign Up")
                }
            }, fontFamily = Poppins)

            Spacer(modifier = Modifier.height(16.dp))
            // input
            OutlinedTextField(value = email.value, onValueChange = { email.value = it },
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
                )
            )

            if (state.error != null) {
                Text(text = state.error, color = red,
                    modifier = Modifier.padding(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // privacy policy
            Text(text = buildAnnotatedString {
                append("By continuing, I agree to the ")
                withStyle(style = SpanStyle(
                    color = primary,
                    fontSize = 14.sp,
                )) {
                    append("Terms of Use ")
                }
                append("& ")
                withStyle(style = SpanStyle(
                    color = primary,
                    fontSize = 14.sp,
                )) {
                    append("Privacy Policy")
                }
            }, fontFamily = Poppins, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(16.dp))
            // button
            Button(
                onClick = {
                    viewModel.resendOtpOrRegister(email = email.value)
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

                Text(text = "Continue", fontFamily = Poppins)
            }


            Spacer(modifier = Modifier.height(16.dp))
            // get help
            Text(text = buildAnnotatedString {
                append("Having trouble logging in? ")
                withStyle(style = SpanStyle(
                    color = primary,
                    fontSize = 14.sp,
                )) {
                    append("Get Help")
                }
            }, fontFamily = Poppins, fontSize = 14.sp)
        }

    }

}

@OptIn(ExperimentalAnimationApi::class)
@Preview(showBackground = true)
@Composable
fun LoginSignUpScreenPreview() {
    LoginSignUpScreen(navController = rememberNavController())
}