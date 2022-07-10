package com.example.myntra.data.api.products.response

import com.example.myntra.domain.model.Product
import com.example.myntra.domain.model.Result

data class AllProductsResponse(
    val message: String,
    val ok: Boolean,
    val products: List<Product>,
    val result: Result
)