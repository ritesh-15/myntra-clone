package com.example.myntra.presentation.navigation_drawer

import androidx.compose.ui.graphics.painter.Painter

data class NavDrawerItem(
    val id: String,
    val title: String,
    val icon: Painter,
)