package com.example.myntra.presentation.single_category_screen

import android.content.pm.ResolveInfo
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
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
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.domain.model.Product
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleCategoryScreen(
    navController: NavController,
    id: String,
    viewModel: SingleCategoryViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current


    val state = viewModel.state.value

    if (state.error != null) {
        Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        navController.popBackStack()
    }

    LaunchedEffect(id, viewModel) {
        viewModel.getSingleCategory(id)
    }

    Scaffold(
        topBar = {
            SingleCategoryTopBar(state.data?.category?.name ?: "")
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
                        .padding(4.dp).clip(CircleShape)
                )
            }
        } else if(state.data?.category?.Product?.size == 0){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(light),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "No Products Found!", fontFamily = Poppins)
            }
        }
        else {
            LazyVerticalGrid(
                cells = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.White)
                    .padding(bottom = 16.dp),
            ) {
                items(state.data?.category?.Product ?: emptyList()) { product ->
                   SingleProduct(product = product, navController)
                }
            }
        }


    }

}

@Composable
fun SingleProduct(product: Product, navController: NavController) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .clickable {
                navController.navigate(Screen.SingleProductScreen.passId(product.id))
            },
        shape = RectangleShape,
        elevation = 0.dp
    ) {

        Column {
            if (product.images.size > 1) {
                AsyncImage(model = product.images[0].url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp)
                )
            }

            Column(
                modifier = Modifier.padding(8.dp)
            ) {


                Text(text = product.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins, maxLines = 1,
                    overflow = TextOverflow.Ellipsis)

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
fun SingleCategoryTopBar(categoryName: String) {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp, title = {
            Text(text = categoryName, fontFamily = Poppins)
        }, actions = {
            Image(painter = painterResource(id = R.drawable.ic_bag),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        })
}

@Preview(showBackground = true)
@Composable
fun SingleCategoryScreenPreview() {
    SingleCategoryScreen(navController = rememberNavController(), id = "")
}