package com.example.myntra.presentation.home_screen

import com.example.myntra.data.api.products.response.AllProductsResponse
import com.example.myntra.domain.model.Product

data class HomeScreenVIewModelState(
    val loading: Boolean = false,
    val data: AllProductsResponse? = null,
    val error:String? = "",
    val products:List<Product> = emptyList()
)
