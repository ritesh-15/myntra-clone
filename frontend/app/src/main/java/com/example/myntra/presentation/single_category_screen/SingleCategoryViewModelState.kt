package com.example.myntra.presentation.single_category_screen


import com.example.myntra.data.api.products.response.SingleCategoryResponse

data class SingleCategoryViewModelState(
    val loading: Boolean = false,
    val data: SingleCategoryResponse? = null,
    val error:String? = null
)