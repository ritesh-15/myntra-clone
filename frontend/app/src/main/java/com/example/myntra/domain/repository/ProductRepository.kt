package com.example.myntra.domain.repository

import com.example.myntra.data.api.products.response.AllCategoriesResponse
import com.example.myntra.data.api.products.response.AllProductsResponse
import com.example.myntra.data.api.products.response.SingleCategoryResponse
import com.example.myntra.data.api.products.response.SingleProductResponse

interface ProductRepository {

    suspend fun getAllProducts(page:Int, size:Int):AllProductsResponse

    suspend fun getSingleProduct(id:String):SingleProductResponse

    suspend fun getAllCategories():AllCategoriesResponse

    suspend fun getSingleCategory(id:String):SingleCategoryResponse

}