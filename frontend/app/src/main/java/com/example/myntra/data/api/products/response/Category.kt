package com.example.myntra.data.api.products.response

import com.example.myntra.domain.model.Product

data class Category(
    val Product: List<Product>,
    val createdAt: String,
    val id: String,
    val name: String,
    val updatedAt: String
)