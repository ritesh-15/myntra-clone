package com.example.myntra.presentation.single_category_screen
import com.example.myntra.data.remote.api.products.response.Category
import com.example.myntra.domain.model.Catagory

data class SingleCategoryViewModelState(
    val loading: Boolean = false,
    val category: Category? = null,
    val error:String? = null
)