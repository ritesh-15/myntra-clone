package com.example.myntra.presentation.home_screen

import com.example.myntra.data.remote.api.products.response.AllProductsResponse
import com.example.myntra.domain.model.Product

data class HomeScreenVIewModelState(
    val loading: Boolean = false,
    val products:List<Product>? = emptyList(),
    val error:String? = "",
)
