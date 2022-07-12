package com.example.myntra.domain.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.ProductAndCategoryWithSizeAndImage
import com.example.myntra.data.remote.api.products.response.AllCategoriesResponse
import com.example.myntra.data.remote.api.products.response.AllProductsResponse
import com.example.myntra.data.remote.api.products.response.SingleCategoryResponse
import com.example.myntra.data.remote.api.products.response.SingleProductResponse
import com.example.myntra.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

     fun getAllProducts(page:Int, size:Int):Flow<Resource<List<Product>>>

    suspend fun getSingleProduct(id:String):SingleProductResponse

    suspend fun getAllCategories():AllCategoriesResponse

    suspend fun getSingleCategory(id:String):SingleCategoryResponse

}