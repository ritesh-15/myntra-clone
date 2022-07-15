package com.example.myntra.presentation.order_history

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.data.remote.api.order.response.Order
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary

@Composable
fun OrderHistoryScreen(
    navController: NavController,
    viewModel: OrderHistoryViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp, title = {
                    Text(text = "Order History", fontFamily = Poppins)
                }, navigationIcon = ({
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = null)
                    }
                }))
        },
        scaffoldState = scaffoldState,
    ) {


        if (state.loading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(light),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
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
        } else {
            if (state.orders.isEmpty()) {
                EmptyOrders()
            } else {
                LazyColumn(modifier = Modifier.padding(it)) {
                    items(state.orders) { order ->
                        OrderCard(order, navController)
                    }
                }
            }
        }


    }

}

@Composable
fun EmptyOrders() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.ic_empty_cart),
            contentDescription = null,
            modifier = Modifier.height(150.dp),
            contentScale = ContentScale.Crop)

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = "You have not have any orders!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Start shopping now!",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            fontFamily = Poppins)
    }
}

@Composable
fun OrderCard(order: Order, navController: NavController) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
            .clickable {
                navController.navigate(Screen.SingleOrderScreen.passId(order.id))
            }
    ) {

        Text(text = order.id, fontFamily = Poppins, fontSize = 14.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Order Status", fontFamily = Poppins, fontSize = 14.sp)
            Text(text = order.extra.orderStatus, fontFamily = Poppins, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(4.dp))
        Text(text = order.address.address, fontFamily = Poppins, fontSize = 14.sp)

    }
    Divider()

    Spacer(modifier = Modifier.height(16.dp))
}

@Preview(showBackground = true)
@Composable
fun OrderHistoryScreenPreview() {
    OrderHistoryScreen(navController = rememberNavController())
}