package com.example.myntra.data.remote.api.products.response

import com.example.myntra.data.local.entity.CategoryEntity
import com.example.myntra.domain.model.Product

data class Category(
    val Product: List<Product>,
    val createdAt: String? = null,
    val id: String,
    val name: String,
    val updatedAt: String? = null,
) {
    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id, name
        )
    }
}