package com.example.myntra.presentation.checkout_screen

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
import com.example.myntra.data.remote.api.order.response.OnlinePayment
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.Shapes
import com.example.myntra.ui.theme.primary
import com.razorpay.Checkout
import org.json.JSONObject

@Composable
fun CheckoutScreen(
    navController: NavController, addressId: String,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current


    val state = viewModel.state.collectAsState().value

    LaunchedEffect(state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(state.done) {
        if (state.done) {
            navController.navigate(Screen.OrderPlaced.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    }

    if (state.loading) {
        Dialog(
            onDismissRequest = { },
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = Shapes.medium)
                    .padding(16.dp),
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

                Spacer(modifier = Modifier.height(12.dp))

                Text(text = "Processing...", fontFamily = Poppins, textAlign = TextAlign.Center)

            }
        }
    }

    val discount = remember {
        mutableStateOf(0)
    }

    val total = remember {
        mutableStateOf(0)
    }

    val totalAmount = remember {
        mutableStateOf(0)
    }

    fun razorPay(payment: OnlinePayment) {
        try {
            val checkout = Checkout()
            checkout.setKeyID("rzp_test_6gToaqyPuWXxYW")

            val options = JSONObject()
            options.put("name", state.user?.name)
            options.put("description", "Products order payment!")
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("order_id", payment.id);
            options.put("amount", payment.amount)

            val prefill = JSONObject()
            prefill.put("email", state.user?.email)
            prefill.put("contact", state.user?.phoneNumber)
            options.put("prefill", prefill)
            checkout.open(context as Activity?, options)
        } catch (e: Exception) {

        }
    }

    LaunchedEffect(state.payment) {
        if (state.payment != null) {
            viewModel.createOrder(addressId,
                discount.value,
                PaymentMethods.Cards,
                totalAmount.value,
                razorPayOrderId = state.payment.id
            )
            Checkout.preload(context)
            razorPay(state.payment)
        }
    }



    fun calculatePrice() {
        val products = viewModel.state.value.cartProducts
        discount.value = 0;
        total.value = 0;
        totalAmount.value = 0

        products?.map { it ->
            discount.value += (
                    if (it.product.discount == 0) 0 else it.product.originalPrice - it.product.discountPrice
                    ) * it.quantity
            total.value += it.product.originalPrice * it.quantity
            totalAmount.value += if (it.product.discount == 0)
                it.product.originalPrice * it.quantity
            else it.product.discountPrice * it.quantity
        }

    }

    LaunchedEffect(state.cartProducts) {
        calculatePrice()
    }


    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp, title = {
                    Text(text = "Checkout", fontFamily = Poppins)
                }, navigationIcon = ({
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }))
        },
        scaffoldState = scaffoldState,
        bottomBar = {
            CheckoutBottomBar(totalAmount.value, viewModel, addressId, discount.value)
        }
    ) {

        Column(
            modifier = Modifier.padding(it)
        ) {

            // payment details
            PriceDetails(total = total.value,
                discount = discount.value,
                totalAmount = totalAmount.value)

            Spacer(modifier = Modifier.height(16.dp))

            // checkout options
            CheckoutOptions(viewModel)

        }

    }
}

@Composable
fun CheckoutOptions(viewModel: CheckoutViewModel) {
    Column(
        modifier = Modifier.padding(12.dp)
    ) {

        Text(text = "Payment Options",
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize)

        Spacer(modifier = Modifier.height(16.dp))

        CheckoutItem(image = painterResource(id = R.drawable.ic_money),
            title = "CASH ON DELIVERY", methodType = PaymentMethods.CashOnDelivery, viewModel) {
            viewModel.changePaymentMethod(PaymentMethods.CashOnDelivery)
        }

        CheckoutItem(image = painterResource(id = R.drawable.ic_card),
            title = "DEBIT/CREDIT CARD",
            methodType = PaymentMethods.Cards, viewModel) {
            viewModel.changePaymentMethod(PaymentMethods.Cards)
        }

        CheckoutItem(image = painterResource(id = R.drawable.ic_wallet),
            title = "PHONE PAY/GOOGLE PAY/ PAYTM", methodType = PaymentMethods.Wallet, viewModel) {
            viewModel.changePaymentMethod(PaymentMethods.Wallet)
        }

    }
}

@Composable
fun CheckoutItem(
    image: Painter,
    title: String,
    methodType: PaymentMethods,
    viewModel: CheckoutViewModel,
    onSelect: () -> Unit,
) {

    fun isSelected(): Boolean {
        return methodType == viewModel.selectedPaymentMethod.value
    }

    Card(
        elevation = 0.dp,
        backgroundColor = Color.White,
        modifier = Modifier
            .selectable(
                selected = isSelected()
            ) {
                onSelect()
            }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            // icon
            Icon(painter = image,
                contentDescription = null,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp),
                tint = if (isSelected()) primary else Color.Black
            )

            Spacer(modifier = Modifier.width(8.dp))

            // title
            Text(text = title,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = if (isSelected()) primary else Color.Black
            )
        }
    }
    Divider()
}

// price details
@Composable
fun PriceDetails(total: Int, totalAmount: Int, discount: Int) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .padding(12.dp)
    ) {
        Text(text = "Price Detail",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins, maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        PriceRow("Total MRP", "₹ $total")
        Spacer(modifier = Modifier.height(4.dp))
        PriceRow("Discount on MRP", "₹ $discount", discount = true)
        Spacer(modifier = Modifier.height(8.dp))
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
fun PriceRow(title: String, value: String, discount: Boolean = false) {
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
            overflow = TextOverflow.Ellipsis,
            color = if (discount) Color.Green else Color.Black
        )
    }
}

@Composable
fun CheckoutBottomBar(amount: Int, viewModel: CheckoutViewModel, addressId: String, discount: Int) {

    fun handlePayment() {
        if (viewModel.selectedPaymentMethod.value == PaymentMethods.CashOnDelivery) {
            // cash on delivery payment
            viewModel.createOrder(addressId, discount, PaymentMethods.CashOnDelivery, amount)
        } else {
            // online payment
            viewModel.makePaymentFromBackend(amount)
        }
    }


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
                handlePayment()
            },
            elevation = ButtonDefaults.elevation(0.dp),
            enabled = true
        ) {

            if (false) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .height(20.dp)
                        .width(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            }
            Text(text = "pay ₹ $amount".uppercase(),
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(navController = rememberNavController(), "")
}