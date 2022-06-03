package com.example.myntra

sealed class Screen(
    val route:String
){
    object Home : Screen("home_screen")
    object Categories : Screen("categories_screen")
    object Explore  : Screen("explore_screen")
    object Profile  : Screen("profile_screen")
}