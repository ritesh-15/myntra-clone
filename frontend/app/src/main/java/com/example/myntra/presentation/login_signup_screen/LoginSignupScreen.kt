package com.example.myntra.presentation.login_signup_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.primary

@Composable
fun LoginSignUpScreen(navController: NavController) {

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                },
                actions = {},
                title = {},
                backgroundColor = Color.White,
                elevation = 0.dp
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
            OutlinedTextField(value = "", onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Email Address", fontFamily = Poppins)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email
                )
            )

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
            }, fontFamily = Poppins)

            Spacer(modifier = Modifier.height(16.dp))
            // button
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primary,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Continue", fontFamily = Poppins)
            }

            // get help

        }

    }

}

@Preview(showBackground = true)
@Composable
fun LoginSignUpScreenPreview() {
    LoginSignUpScreen(navController = rememberNavController())
}