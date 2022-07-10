package com.example.myntra.presentation.home_screen

import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.common.nav_drawer.DrawerBody
import com.example.myntra.common.nav_drawer.DrawerHeader
import com.example.myntra.common.nav_drawer.NavDrawerItem
import com.example.myntra.domain.model.Product
import com.example.myntra.domain.model.User
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.dotLight
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userName: String? = null,
    vIewModel: HomeScreenVIewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    // state
    val state = vIewModel.state.value
    val context = LocalContext.current

    if (state.error != null) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }


    Log.d("products", state.toString())


    // Top bar
    Scaffold(
        topBar = {
            HomeScreenTopBar(onNavigationIconClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            })
        },
        drawerContent = {
            DrawerHeader(navController = navController, userName = userName)
            DrawerBody(items = listOf(
                NavDrawerItem(id = "categories",
                    icon = painterResource(id = R.drawable.ic_catogiries_outlined),
                    title = "Shop by Categories"
                ),
                NavDrawerItem(id = "orders",
                    icon = painterResource(id = R.drawable.ic_orders),
                    title = "Orders"
                ),
            ), navController = navController,
                onClick = {})
        },

        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        bottomBar = { AppBottomNavigation(navController = navController) }
    ) {

        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            modifier = Modifier
                .background(light)
                .padding(bottom = 16.dp),
        ) {

            item(span = {
                GridItemSpan(2)
            }) {
                Column {
                    // image slider
                    ImageSlider()

                    Spacer(modifier = Modifier.height(16.dp))

                    // top picks
                    TopPicks()

                    Spacer(modifier = Modifier.height(16.dp))

                    //  featured
                    Featured()

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            items(state.data?.products ?: emptyList()) { product ->
                SingleProduct(product = product)
            }
        }
    }


}


@Composable
fun SingleProduct(product: Product) {
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
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
                fontFamily = Poppins)

            Text(text = product.description,
                fontSize = 12.sp,
                fontFamily = Poppins,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Light
            )

            Row(horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                if (product.discount != 0) {
                    Text(text = product.originalPrice.toString(),
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
                    text = price,
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

@Composable
fun Featured() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Deal Of The Day",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontFamily = MaterialTheme.typography.h6.fontFamily
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(listOf(R.drawable.ic_featured_1,
                R.drawable.ic_featured_2,
                R.drawable.ic_featured_3,
                R.drawable.ic_featured_4
            )) { item ->
                Image(painter = painterResource(id = item),
                    contentDescription = null,
                    modifier = Modifier
                        .height(300.dp)
                        .width(200.dp)
                        .background(Color.White),
                    contentScale = ContentScale.FillBounds,
                    alignment = Alignment.Center
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

    }
}

@Composable
fun TopPicks() {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Top Picks",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize,
            fontFamily = MaterialTheme.typography.h6.fontFamily
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_home_card_1),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .weight(0.5f)
                    .background(Color.White),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )

            Image(painter = painterResource(id = R.drawable.ic_home_card_2),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .weight(0.5f)
                    .background(Color.White),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(painter = painterResource(id = R.drawable.ic_home_card_3),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .weight(0.5f)
                    .background(Color.White),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )

            Image(painter = painterResource(id = R.drawable.ic_home_card_4),
                contentDescription = null,
                modifier = Modifier
                    .height(200.dp)
                    .weight(0.5f)
                    .background(Color.White),
                contentScale = ContentScale.FillBounds,
                alignment = Alignment.Center
            )
        }

    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageSlider() {

    val pagerState = rememberPagerState(4)

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            count = 4,
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            state = pagerState,
        ) { page ->
            val image: Int = when (page) {
                0 -> R.drawable.ic_slider_1
                1 -> R.drawable.ic_slider_2
                2 -> R.drawable.ic_slider_3
                3 -> R.drawable.ic_slider_4
                else -> R.drawable.ic_slider_1
            }

            Image(painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.FillBounds
            )

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


@Composable
fun HomeScreenTopBar(
    onNavigationIconClick: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = onNavigationIconClick) {
                Icon(painter = painterResource(id = R.drawable.ic_menu), contentDescription = null,
                    modifier = Modifier
                        .width(22.dp)
                        .height(22.dp)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Image(painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier
                    .width(35.dp)
                    .height(35.dp),
                contentScale = ContentScale.Fit
            )
        }
        Row {
            Image(painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(22.dp))

            Image(painter = painterResource(id = R.drawable.ic_bell),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(22.dp))

            Image(painter = painterResource(id = R.drawable.ic_like),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)

            Spacer(modifier = Modifier.width(22.dp))

            Image(painter = painterResource(id = R.drawable.ic_bag),
                contentDescription = null,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp),
                contentScale = ContentScale.Fit)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}