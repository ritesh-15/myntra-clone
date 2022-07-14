package com.example.myntra.presentation.order_placed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.isPopupLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.primary

@Composable
fun OrderPlacedScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(imageVector = Icons.Outlined.CheckCircle, contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Order Placed Successfully!",
            fontFamily = Poppins,
            fontSize = MaterialTheme.typography.h5.fontSize, textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))


        Text(text = "Thank you for ordering from our app, you can view your orders status from order history!",
            fontFamily = Poppins,
            fontSize = 14.sp, textAlign = TextAlign.Center,
            fontWeight = FontWeight.Light
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                navController.navigate(Screen.OrderHistoryScreen.route) {
                    popUpTo(Screen.OrderPlaced.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primary,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(vertical = 12.dp),
        ) {
            Text(text = "got to order".uppercase(), fontFamily = Poppins)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderPlacedScreenPreview() {
    OrderPlacedScreen(navController = rememberNavController())
}