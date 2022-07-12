package com.example.myntra.data.remote.api.products.response

import com.example.myntra.domain.model.Catagory

data class AllCategoriesResponse(
    val categories: List<Catagory>,
    val message: String,
    val ok: Boolean
)