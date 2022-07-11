package com.example.myntra.presentation.categories_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myntra.R
import com.example.myntra.ui.theme.Poppins

@Composable
fun CategoryScreenTopBar() {
    TopAppBar(
        backgroundColor = Color.White,
        elevation = 0.dp
        ,title = {
        Text(text = "Categories", fontFamily = Poppins)
    }, actions = {
        Image(painter = painterResource(id = R.drawable.ic_bag),
            contentDescription = null,
            modifier = Modifier
                .width(22.dp)
                .height(22.dp),
            contentScale = ContentScale.Fit)
    })
}