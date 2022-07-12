package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.CategoryEntity

data class Catagory(
    val id: String,
    val name: String,
) {
    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id, name
        )
    }
}