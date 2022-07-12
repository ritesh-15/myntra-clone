package com.example.myntra.presentation.bag_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.domain.model.Product
import com.example.myntra.presentation.single_category_screen.SingleCategoryTopBar
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.primary

@Composable
fun BagScreen(
    navController: NavController,
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            BagScreenTopBar()
        },
        scaffoldState = scaffoldState,
    ) {
        LazyColumn(){
            items(10){
                CartProduct(null)
            }
            item{
                PriceDetails()
            }
        }
    }
}

@Composable
fun PriceDetails() {
    Column {
        Text(text = "Price Details (1 Items)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins, maxLines = 1,
            overflow = TextOverflow.Ellipsis)
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        PriceRow("Total MRP", "1499")
        PriceRow("Discount on MRP", "874")
        Spacer(modifier = Modifier.height(4.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(text = "Total Amount",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins, maxLines = 1,
                overflow = TextOverflow.Ellipsis)

            Text(text = "524",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins, maxLines = 1,
                overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun PriceRow(title:String, value:String) {
    Row {
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
fun CartProduct(product: Product?) {

    if (product == null) return

    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = Color.White,
        modifier = Modifier
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            // image
            AsyncImage(model = product.images[0], contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(100.dp)
            )

            Column(

            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins, maxLines = 1,
                        overflow = TextOverflow.Ellipsis)

                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
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

                    if (product.discount != 0) {
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
            }

        }
    }

}

@Composable
fun BagScreenTopBar() {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp, title = {
            Text(text = "Shopping Cart", fontFamily = Poppins)
        }, actions = {
            Image(painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        }, modifier = Modifier.padding(horizontal = 8.dp), navigationIcon = ({
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }))
}

@Preview(showBackground = true)
@Composable
fun BagScreenPreview() {
    BagScreen(navController = rememberNavController())
}