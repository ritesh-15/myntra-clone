package com.example.myntra.domain.model

data class Product(
    val catagory: Catagory,
    val catagoryId: String,
    val createdAt: String,
    val description: String,
    val discount: Int,
    val discountPrice: Int,
    val fabric: String,
    val fit: String,
    val id: String,
    val images: List<Image>,
    val name: String,
    val originalPrice: Int,
    val sizes: List<Size>,
    val stock: Int,
    val updatedAt: String
)