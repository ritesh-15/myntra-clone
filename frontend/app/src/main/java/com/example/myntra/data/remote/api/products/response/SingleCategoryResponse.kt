package com.example.myntra.data.remote.api.products.response

data class SingleCategoryResponse(
    val category: Category,
    val message: String,
    val ok: Boolean
)