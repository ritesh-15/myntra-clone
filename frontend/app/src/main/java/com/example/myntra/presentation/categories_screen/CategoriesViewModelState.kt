package com.example.myntra.presentation.categories_screen


import com.example.myntra.domain.model.Catagory

data class CategoriesViewModelState(
    val loading: Boolean = false,
    val categories:List<Catagory>? = emptyList(),
    val error:String? = null
)