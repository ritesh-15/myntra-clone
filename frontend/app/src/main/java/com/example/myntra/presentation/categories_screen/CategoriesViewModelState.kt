package com.example.myntra.presentation.categories_screen


import com.example.myntra.data.api.products.response.AllCategoriesResponse

data class CategoriesViewModelState(
    val loading: Boolean = false,
    val data: AllCategoriesResponse? = null,
    val error:String? = null
)