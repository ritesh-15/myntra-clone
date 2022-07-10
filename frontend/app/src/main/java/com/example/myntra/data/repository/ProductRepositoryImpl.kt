package com.example.myntra.data.repository

import com.example.myntra.data.api.products.ProductInterface
import com.example.myntra.data.api.products.response.AllCategoriesResponse
import com.example.myntra.data.api.products.response.AllProductsResponse
import com.example.myntra.data.api.products.response.SingleCategoryResponse
import com.example.myntra.data.api.products.response.SingleProductResponse
import com.example.myntra.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api:ProductInterface
): ProductRepository {
    override suspend fun getAllProducts(page: Int, size: Int): AllProductsResponse {
        return api.getAllProducts(page, size)
    }

    override suspend fun getSingleProduct(id: String): SingleProductResponse {
       return api.getSingleProduct(id)
    }

    override suspend fun getAllCategories(): AllCategoriesResponse {
        return api.getAllCategories()
    }

    override suspend fun getSingleCategory(id: String): SingleCategoryResponse {
        return api.getSingleCategory(id)
    }
}