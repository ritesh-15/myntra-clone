package com.example.myntra.presentation.profile_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.domain.model.User
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.Shapes
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileScreenViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()

    val state = viewModel.state.value

    if (state.loading) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = Shapes.medium),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = primary,
                    strokeWidth = 2.dp,
                    modifier = Modifier
                        .background(Color.White)
                        .padding(4.dp)
                        .clip(CircleShape)
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                contentPadding = PaddingValues(horizontal = 12.dp)
            ) {
                Text(text = "Profile", fontFamily = Poppins)
            }
        },
        scaffoldState = scaffoldState,
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) {


        if (state.user != null) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                ProfileHeader(state.user)

                Spacer(modifier = Modifier.height(16.dp))

                ProfileItem(icon = Icons.Outlined.CheckCircle,
                    "Orders",
                    "Check your order history") {
                    navController.navigate(Screen.OrderHistoryScreen.route)
                }

                ProfileItem(icon = Icons.Default.LocationOn,
                    "Addresses", "Check your addresses") {
                    navController.navigate(Screen.AddressesScreen.route )
                }

                ProfileItem(icon = Icons.Default.AccountCircle,
                    "Profile Details",
                    "Change your profile details") {
                    navController.navigate(Screen.UpdateProfileScreen.route)
                }

                ProfileItem(icon = Icons.Default.FavoriteBorder,
                    "Wishlist",
                    "Your most loved products") {

                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.logout()
                },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = primary,
                        backgroundColor = Color.White
                    ),
                    contentPadding = PaddingValues(12.dp),
                    border = BorderStroke(
                        width = 1.dp,
                        color = primary
                    ),
                    shape = Shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "log out".uppercase(), fontFamily = Poppins, fontSize = 16.sp)
                }

            }
        } else {
            NoUserScreen(navController)
        }

    }
}

@Composable
fun NoUserScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(painter = painterResource(id = R.drawable.ic_no_data),
            contentDescription = null,
            modifier = Modifier.height(150.dp),
            contentScale = ContentScale.Fit)

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "You are not Logged In!",
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Log in and get access to your orders and profile data",
            fontFamily = Poppins,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp, textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            navController.navigate(Screen.LoginSignUp.route) {
                popUpTo(Screen.ProfileScreen.route) {
                    inclusive = true
                }
            }
        },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = primary
            ),
            contentPadding = PaddingValues(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Log In / Sign Up",
                fontFamily = Poppins,
                fontSize = 16.sp)
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileItem(icon: ImageVector, title: String, description: String, onClick: () -> Unit) {
    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = Color.White,
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 16.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null)

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Text(text = title,
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = description,
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light)
            }

        }
    }

    Divider()
}

@Composable
fun ProfileHeader(user: User) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
    ) {

        Text(text = user.name, fontFamily = Poppins, fontWeight = FontWeight.Bold, fontSize = 16.sp)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = user.email,
            fontFamily = Poppins,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(navController = rememberNavController())
}