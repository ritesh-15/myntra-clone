package com.example.myntra.presentation.single_order_screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.data.remote.api.order.response.Order
import com.example.myntra.data.remote.api.order.response.ProductX
import com.example.myntra.domain.model.Product
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary

@Composable
fun SingleOrderScreen(
    navController: NavController,
    id: String,
    viewModel: SingleOrderViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val state = viewModel.state.value

    LaunchedEffect(id) {
        viewModel.getOrder(id)
    }

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) {
            navController.navigate(Screen.OrderHistoryScreen.route) {
                popUpTo(Screen.SingleOrderScreen.route) {
                    inclusive = true
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp, title = {
                    Text(text = "Your Order", fontFamily = Poppins)
                }, navigationIcon = ({
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = null)
                    }
                }))
        },
        scaffoldState = scaffoldState,
        bottomBar = {
            if (!state.loading) {
                SingleOrderBottomBar(viewModel, id)
            }
        }
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
        } else if (state.order != null) {
            LazyColumn(
                modifier = Modifier
                    .padding(it)
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                // order details
                item {
                    OrderDetails(order = state.order)
                }

                // address details
                item {
                    PaymentDetails(order = state.order)
                }

                // products
                item {
                    Column {
                        Text(text = "Products",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp)

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                items(state.order.products) {
                    ProductItem(it, navController)
                }
            }
        }


    }

}


@Composable
fun SingleOrderBottomBar(viewModel: SingleOrderViewModel, id: String) {
    BottomNavigation(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
    ) {

        Button(
            modifier = Modifier
                .background(primary)
                .fillMaxSize(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = primary,
                contentColor = Color.White
            ),
            onClick = {
                viewModel.deleteOrder(id)
            },
            elevation = ButtonDefaults.elevation(0.dp),
        ) {
            Text(text = "Delete order".uppercase(), fontFamily = Poppins)
        }
    }
}


@Composable
fun ProductItem(
    cart: com.example.myntra.data.remote.api.order.response.Product,
    navController: NavController,
) {

    val product = cart.product

    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 16.dp)
            .clickable {
                navController.navigate(Screen.SingleProductScreen.passId(product.id))
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {


            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins, maxLines = 1,
                        overflow = TextOverflow.Ellipsis)

                }


                Text(text = product.description,
                    fontSize = 12.sp,
                    fontFamily = Poppins,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    maxLines = 1
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    if (product.discount.let { it ?: "" } != 0) {
                        Text(text = "₹ ${product.originalPrice}",
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Light,
                            textDecoration = TextDecoration.LineThrough
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    val price = if (product.discount == 0) {
                        product.originalPrice.toString()
                    } else {
                        product.discountPrice.toString()
                    }

                    Text(
                        text = "₹ $price",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    if (product.discount != 0) {
                        Text(
                            text = "${product.discount}% off",
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                            color = primary,
                            fontWeight = FontWeight.Light
                        )
                    }

                }


                Spacer(modifier = Modifier.width(12.dp))

                // quantity
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    // size
                    Box(modifier = Modifier
                        .width(25.dp)
                        .height(25.dp)
                        .border(width = 1.dp,
                            color = Color.Black.copy(alpha = 0.7f),
                            shape = CircleShape)
                        .padding(4.dp),
                        contentAlignment = Alignment.Center)
                    {
                        Text(
                            text = cart.size.title,
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1,
                        )
                    }

                }

            }

        }
    }
}

@Composable
fun PaymentDetails(order: Order) {
    Column {
        Text(text = "Payment Details",
            fontFamily = Poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(12.dp))
        RowItem(title = "Payment Id", value = order.payment[0].id)
        RowItem(title = "Payment Status",
            value = if (order.payment[0].paymentStatus) "Paid" else "Not Paid")
        RowItem(title = "Payment Type", value = order.payment[0].paymentType)
    }
}

@Composable
fun OrderDetails(order: Order) {
    Column {
        Text(text = "Order Details",
            fontFamily = Poppins,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Divider()

        RowItem(title = "Order Id", value = order.id)
        RowItem(title = "Order Status", value = order.extra.orderStatus)
    }
}

@Composable
fun RowItem(title: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, fontFamily = Poppins, fontSize = 14.sp)
        Text(text = value, fontFamily = Poppins, fontSize = 14.sp, fontWeight = FontWeight.Light)
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
fun SingleOrderScreenPreview() {
    SingleOrderScreen(navController = rememberNavController(), "")
}