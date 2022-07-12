package com.example.myntra.presentation.single_product_screen

import com.example.myntra.domain.model.Product

data class SingleProductVIewModelState(
    val loading: Boolean = false,
    val product:Product? = null,
    val error:String? = null
)