package com.example.myntra.data.api.products

import com.example.myntra.data.api.products.response.AllCategoriesResponse
import com.example.myntra.data.api.products.response.AllProductsResponse
import com.example.myntra.data.api.products.response.SingleCategoryResponse
import com.example.myntra.data.api.products.response.SingleProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductInterface {

    @GET("product/all")
    suspend fun getAllProducts(
        @Query("page") page: Int,
        @Query("size") size: Int,
    ): AllProductsResponse

    @GET("product/{id}")
    suspend fun getSingleProduct(
        @Path("id") id: String,
    ): SingleProductResponse

    @GET("product/category/all")
    suspend fun getAllCategories(): AllCategoriesResponse

    @GET("product/category/{id}")
    suspend fun getSingleCategory(@Path("id") id: String): SingleCategoryResponse
}