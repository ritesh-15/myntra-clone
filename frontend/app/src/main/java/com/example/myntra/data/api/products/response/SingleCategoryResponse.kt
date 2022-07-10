package com.example.myntra.data.api.products.response

data class SingleCategoryResponse(
    val category: Category,
    val message: String,
    val ok: Boolean
)