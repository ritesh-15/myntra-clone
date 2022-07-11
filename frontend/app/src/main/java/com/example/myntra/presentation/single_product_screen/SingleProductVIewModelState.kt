package com.example.myntra.presentation.single_product_screen


import com.example.myntra.data.api.products.response.SingleProductResponse

data class SingleProductVIewModelState(
    val loading: Boolean = false,
    val data: SingleProductResponse? = null,
    val error:String? = null
)