package com.example.myntra.data.local.entity

import androidx.room.*
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.model.Product
import java.util.*

@Entity(tableName = "products")
data class ProductEntity(
    val createdAt: String,
    val description: String,
    val discount: Int,
    val discountPrice: Int,
    val fabric: String,
    val fit: String,
    @PrimaryKey val id: String,
    val name: String,
    val originalPrice: Int,
    val stock: Int,
    val updatedAt: String,
    val catagoryId: String,
    @Embedded(prefix = "category_id") val catagory: Catagory,
)

