package com.example.myntra.presentation.categories_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myntra.R
import com.example.myntra.common.Screen
import com.example.myntra.common.bottom_navigation.AppBottomNavigation
import com.example.myntra.common.nav_drawer.DrawerBody
import com.example.myntra.common.nav_drawer.DrawerHeader
import com.example.myntra.common.nav_drawer.NavDrawerItem
import com.example.myntra.domain.model.Catagory
import com.example.myntra.presentation.home_screen.HomeScreenTopBar
import com.example.myntra.ui.theme.Poppins
import com.example.myntra.ui.theme.Shapes
import com.example.myntra.ui.theme.light
import com.example.myntra.ui.theme.primary
import kotlinx.coroutines.launch

@Composable
fun CategoriesScreen(
    navController: NavController,
    viewModel: CategoriesViewModel = hiltViewModel(),
) {

    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        topBar = {
            CategoryScreenTopBar()
        },
        scaffoldState = scaffoldState,
        drawerShape = RectangleShape,
        bottomBar = { AppBottomNavigation(navController = navController) }
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
        }else{
            LazyColumn(
                modifier = Modifier
                    .background(Color.White)
                    .padding(4.dp)
            ) {
                items(state.categories ?: emptyList()) { category ->
                    SingleCategory(category, navController)
                }
            }
        }
    }
}

@Composable
fun SingleCategory(category: Catagory, navController: NavController) {
    Column(
        modifier = Modifier
            .clickable {
                navController.navigate(Screen.SingleCategoryScreen.passId(category.id))
            }
            .background(Color.White)
            .fillMaxWidth()
            .border(width = 1.dp,
                Color.LightGray,
                shape = Shapes.small)
            .padding(16.dp)
    ) {
        Text(
            text = category.name,
            fontSize = 16.sp,
            fontFamily = Poppins,
        )
    }

    Spacer(modifier = Modifier.height(12.dp))
}

@Preview(showBackground = true)
@Composable
fun CategoriesScreenPreview() {
    CategoriesScreen(navController = rememberNavController())
}