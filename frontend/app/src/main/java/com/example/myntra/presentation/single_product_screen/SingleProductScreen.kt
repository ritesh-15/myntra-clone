package com.example.myntra.presentation.single_product_screen

import android.hardware.lights.Light
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myntra.R
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.data.local.entity.CartEntity
import com.example.myntra.domain.model.Cart
import com.example.myntra.domain.model.Image
import com.example.myntra.domain.model.Product
import com.example.myntra.presentation.single_category_screen.SingleCategoryTopBar
import com.example.myntra.presentation.single_category_screen.SingleCategoryViewModel
import com.example.myntra.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleProductScreen(
    navController: NavController,
    id: String,
    viewModel: SingleProductViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current

    val state = viewModel.state.value

    if (state.error != null) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    LaunchedEffect(id, viewModel) {
        viewModel.getSingleProduct(id)
    }

    Scaffold(
        topBar = {
            SingleProductTopBar(state.product?.name ?: "")
        },
        scaffoldState = scaffoldState,
        bottomBar = {
            if (state.product != null) {
                SingleProductBottomBar(viewModel, state.product)
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
        } else if (!state.loading && state.product != null) {
            val product = state.product

            Column(
                modifier = Modifier
                    .background(light)
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = 50.dp)
            ) {

                ImageSlider(product.images)

                Column(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .background(Color.White)
                        .fillMaxWidth()
                ) {
                    Text(text = product.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins)

                    Text(text = product.description,
                        fontSize = 14.sp,
                        fontFamily = Poppins,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light
                    )

                    Row(horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically) {

                        if (product.discount != 0) {
                            Text(text = "₹ ${product.originalPrice}",
                                fontSize = 16.sp,
                                fontFamily = Poppins,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Light,
                                textDecoration = TextDecoration.LineThrough
                            )

                            Spacer(modifier = Modifier.width(6.dp))
                        }

                        val price = if (product.discount == 0) {
                            product.originalPrice.toString()
                        } else {
                            product.discountPrice.toString()
                        }

                        Text(
                            text = "₹ $price",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins,
                            overflow = TextOverflow.Ellipsis,
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        if (product.discount != 0) {
                            Text(
                                text = "${product.discount}% off",
                                fontSize = 16.sp,
                                fontFamily = Poppins,
                                overflow = TextOverflow.Ellipsis,
                                color = primary,
                                fontWeight = FontWeight.Light
                            )
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Select Size",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold)

                    Spacer(modifier = Modifier.height(12.dp))

                    LazyRow {
                        items(product.sizes) { size ->
                            Box(
                                modifier = Modifier
                                    .width(35.dp)
                                    .height(35.dp)
                                    .border(
                                        width = 1.dp,
                                        color = Color.LightGray,
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = size.title, fontFamily = Poppins)
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(12.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Product Details",
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))

                    GridItem(title = "Fabric", value = product.fabric)
                    Spacer(modifier = Modifier.height(8.dp))
                    GridItem(title = "Fit", value = product.fit)


                }

            }
        }
    }

}

@Composable
fun GridItem(title: String, value: String) {
    Column(
        modifier = Modifier.wrapContentWidth()
    ) {
        Text(text = title, fontFamily = Poppins, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        Text(text = value, fontFamily = Poppins, fontSize = 14.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(4.dp))
        Divider()

    }
}

@Composable
fun SingleProductBottomBar(viewModel: SingleProductViewModel, product: Product) {
    BottomNavigation(
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically

        ) {

            Button(
                modifier = Modifier
                    .weight(1f),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                )
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_like),
                        contentDescription = null,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "wish list".uppercase(), fontFamily = Poppins)
                }

            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = primary,
                    contentColor = Color.White
                ),
                onClick = {
                    viewModel.addToCart(
                        CartEntity(
                            productId = product.id,
                            quantity = 1,
                            id = UUID.randomUUID().toString()
                        )
                    )
                }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(painter = painterResource(id = R.drawable.ic_bag),
                        contentDescription = null,
                        modifier = Modifier
                            .width(18.dp)
                            .height(18.dp))

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "add to bag".uppercase(), fontFamily = Poppins)
                }
            }
        }
    }
}


@Composable
fun SingleProductTopBar(productName: String) {
    TopAppBar(
        backgroundColor = Color.White,
        navigationIcon = {
            Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
        },
        elevation = 0.dp, title = {
            Text(text = productName, fontFamily = Poppins)
        }, actions = {
            Image(painter = painterResource(id = R.drawable.ic_bag),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        })
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider(images: List<Image>) {
    val pagerState = rememberPagerState(initialPage = 1)
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = images.size,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .height(500.dp)
                .fillMaxWidth(),
            state = pagerState,
        ) { page ->
            val image = images[page]

            AsyncImage(model = image.url,
                contentDescription = null,
                modifier = Modifier.height(500.dp), contentScale = ContentScale.FillBounds)
        }

        Spacer(modifier = Modifier.padding(4.dp))

        HorizontalPagerIndicator(pagerState = pagerState,
            inactiveColor = dotLight,
            activeColor = primary,
            indicatorWidth = 6.dp,
            indicatorHeight = 6.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SingleProductScreenPreview() {
    SingleProductScreen(navController = rememberNavController(), id = "")
}