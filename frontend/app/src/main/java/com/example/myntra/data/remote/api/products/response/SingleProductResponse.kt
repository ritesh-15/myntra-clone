package com.example.myntra.data.remote.api.products.response

import com.example.myntra.domain.model.Product

data class SingleProductResponse(
    val message: String,
    val ok: Boolean,
    val product: Product
)