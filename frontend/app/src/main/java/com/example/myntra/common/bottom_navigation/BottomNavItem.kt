package com.example.myntra.common.bottom_navigation

import com.example.myntra.R
import com.example.myntra.common.Screen


sealed class BottomNavItem(
    val title:String,
    val route:String,
    val icon:Int,
    val selectedIcon:Int
){

    object Home: BottomNavItem(
        title = "Home",
        route = Screen.Home.route,
        icon = R.drawable.ic_home_outlined,
        selectedIcon = R.drawable.ic_home_filled
    )

    object Categories: BottomNavItem(
        title = "Categories",
        route = Screen.Categories.route,
        icon = R.drawable.ic_catogiries_outlined,
        selectedIcon = R.drawable.ic_catogiries_filled
    )

    object Explorer: BottomNavItem(
        title = "Explorer",
        route = Screen.Explore.route,
        icon = R.drawable.ic_explorer_outlined,
        selectedIcon = R.drawable.ic_explorer_filled
    )

    object Profile: BottomNavItem(
        title = "Profile",
        route = Screen.Profile.route,
        icon = R.drawable.ic_user_outlined,
        selectedIcon = R.drawable.ic_user_solid
    )
}
