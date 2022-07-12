package com.example.myntra.presentation.bag_screen


import com.example.myntra.domain.model.Cart

data class CartViewModelState(
    val loading: Boolean = false,
    val products:List<Cart>? = emptyList(),
    val error:String? = null
)