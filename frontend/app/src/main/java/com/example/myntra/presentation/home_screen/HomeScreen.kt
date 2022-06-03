package com.example.myntra.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myntra.R
import com.example.myntra.presentation.navigation_drawer.DrawerBody
import com.example.myntra.presentation.navigation_drawer.DrawerHeader
import com.example.myntra.presentation.navigation_drawer.NavDrawerItem
import com.example.myntra.ui.theme.dotLight
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun HomeScreen() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

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
            DrawerHeader()
            DrawerBody(items = listOf(
                NavDrawerItem(id = "categories",
                    icon = painterResource(id = R.drawable.ic_catogiries_outlined),
                    title = "Shop by Categories"
                ),
                NavDrawerItem(id = "orders",
                    icon = painterResource(id = R.drawable.ic_orders),
                    title = "Orders"
                )
            ),
                onClick = {})
        },
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(light)
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            // image slider
            ImageSlider()

            Spacer(modifier = Modifier.height(16.dp))

            // top picks
            TopPicks()

            Spacer(modifier = Modifier.height(16.dp))
            //  featured
            Featured()

            Spacer(modifier = Modifier.height(16.dp))
            //  featured
            Featured()

            Spacer(modifier = Modifier.height(16.dp))
            //  featured
            Featured()

            Spacer(modifier = Modifier.height(16.dp))
            // top picks
            TopPicks()

            Spacer(modifier = Modifier.height(16.dp))
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
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
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
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif
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
    HomeScreen()
}