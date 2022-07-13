package com.example.myntra.presentation.bag_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.model.Product
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary

@Composable
fun BagScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    val discount = remember {
        mutableStateOf(0)
    }

    val total = remember {
        mutableStateOf(0)
    }

    val totalAmount = remember {
        mutableStateOf(0)
    }

    fun calculatePrice() {
        val products = viewModel.state.value.products
        discount.value = 0;
        total.value = 0;
        totalAmount.value = 0

        products?.map { it ->
            discount.value += (it.product.originalPrice - it.product.discountPrice) * it.quantity
            total.value += it.product.originalPrice * it.quantity
            totalAmount.value += if (it.product.discount == 0)
                it.product.originalPrice * it.quantity
            else it.product.discountPrice * it.quantity
        }

    }

    fun removeFromCart(product: Product) {
        viewModel.removeFromCart(product.id)
    }

    LaunchedEffect(state.products) {
        calculatePrice()
    }


    Scaffold(
        topBar = {
            BagScreenTopBar(navController)
        },
        scaffoldState = scaffoldState,
        bottomBar = {
            if (state.products?.size != 0) {
                BagScreenBottomBar()
            }
        }
    ) {
        if (state.products?.size != 0) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(light)
                    .padding(bottom = 50.dp)
            ) {
                items(state.products ?: emptyList()) { cart ->
                    CartProduct(cart,
                        viewModel,
                        removeFromCart = { it -> removeFromCart(it) },
                        calculatePrice = {})
                }

                item {
                    PriceDetails(total.value, totalAmount.value, discount.value)
                }
            }
        } else {
            EmptyCart()
        }
    }
}

@Composable
fun EmptyCart() {
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

        Text(text = "Cart Is Empty!",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = "Start shopping by adding product to cart",
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            fontFamily = Poppins)
    }
}

@Composable
fun BagScreenBottomBar() {
    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primary,
                    contentColor = Color.White
                ),
                onClick = {

                },
                elevation = ButtonDefaults.elevation(0.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                Text(text = "place order".uppercase(), fontFamily = Poppins)
            }
        }

    }
}

@Composable
fun PriceDetails(total: Int, totalAmount: Int, discount: Int) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
    ) {
        Text(text = "Price Details (1 Items)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins, maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        PriceRow("Total MRP", "₹ $total")
        PriceRow("Discount on MRP", "₹ $discount")
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Total Amount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins, maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Text(text = "₹ $totalAmount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins, maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun PriceRow(title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            fontFamily = Poppins, maxLines = 1,
            overflow = TextOverflow.Ellipsis)

        Text(text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            fontFamily = Poppins, maxLines = 1,
            overflow = TextOverflow.Ellipsis)
    }
}

@Composable
fun CartProduct(
    cart: Cart,
    viewModel: CartViewModel,
    calculatePrice: () -> Unit,
    removeFromCart: (product: Product) -> Unit,
) {
    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = Color.White,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            // image
            AsyncImage(model = cart.product.images[0].url, contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = cart.product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins, maxLines = 1,
                        overflow = TextOverflow.Ellipsis)

                    IconButton(onClick = {
                        removeFromCart(cart.product)
                        viewModel.getAllCartProducts()
                    }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }


                Text(text = cart.product.description,
                    fontSize = 12.sp,
                    fontFamily = Poppins,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Light,
                    maxLines = 1
                )

                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {

                    if (cart.product.discount.let { it ?: "" } != 0) {
                        Text(text = "₹ ${cart.product.originalPrice}",
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Light,
                            textDecoration = TextDecoration.LineThrough
                        )

                        Spacer(modifier = Modifier.width(4.dp))
                    }

                    val price = if (cart.product.discount == 0) {
                        cart.product.originalPrice.toString()
                    } else {
                        cart.product.discountPrice.toString()
                    }

                    Text(
                        text = "₹ $price",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.width(4.dp))

                    if (cart.product.discount != 0) {
                        Text(
                            text = "${cart.product.discount}% off",
                            fontSize = 12.sp,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                            color = primary,
                            fontWeight = FontWeight.Light
                        )
                    }

                }

            }

        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}


@Composable
fun BagScreenTopBar(navController: NavController) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp, title = {
            Text(text = "Shopping Cart", fontFamily = Poppins)
        },
        actions = {
            Image(painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        },
        modifier = Modifier.padding(horizontal = 8.dp),
        navigationIcon = ({
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        }))
}

@Preview(showBackground = true)
@Composable
fun BagScreenPreview() {
    BagScreen(navController = rememberNavController())
}