package com.example.myntra.common.bottom_navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myntra.common.Screen
import com.example.myntra.ui.theme.primary

@Composable
fun AppBottomNavigation(navController: NavController) {
    val navItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Categories,
        BottomNavItem.Explorer,
        BottomNavItem.Profile
    )

    BottomNavigation(
        backgroundColor = Color.White
    ) {
        val currentBackStackEntry = navController.currentBackStackEntry
        val route = currentBackStackEntry?.destination?.route

        navItems.forEach { item ->
            val selected = route == item.route

            BottomNavigationItem(selected = selected, onClick = {
                navController.navigate(item.route) {
                    popUpTo(item.route) {
                        inclusive = true
                    }
                }
            },
                icon = {
                    if (selected) {
                        Icon(painter = painterResource(id = item.selectedIcon),
                            contentDescription = null,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    } else {
                        Icon(painter = painterResource(id = item.icon), contentDescription = null,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    }
                },
                label = {
                    Text(text = item.title,
                        fontSize = 10.sp,
                        fontFamily = MaterialTheme.typography.subtitle2.fontFamily
                    )
                },
                selectedContentColor = primary,
                unselectedContentColor = Color.Black
            )
        }
    }
}