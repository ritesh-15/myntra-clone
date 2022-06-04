package com.example.myntra.ui.theme

import android.sax.TextElementListener
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myntra.R

val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold_copy, FontWeight.Bold),
    Font(R.font.poppins_light_copy, FontWeight.Light),
    Font(R.font.poppins_semibold_copy, FontWeight.SemiBold),
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = Poppins,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontFamily = Poppins
    ),
    h2 = TextStyle(
        fontFamily = Poppins
    ),
    h3 = TextStyle(
        fontFamily = Poppins
    ),
    h4 = TextStyle(
        fontFamily = Poppins
    ),
    h5 = TextStyle(
        fontFamily = Poppins
    ),
    h6 = TextStyle(
        fontFamily = Poppins
    ),
    subtitle1 = TextStyle(
        fontFamily = Poppins
    ),
    subtitle2 = TextStyle(
        fontFamily = Poppins
    ),
    body2 = TextStyle(
        fontFamily = Poppins
    )
)