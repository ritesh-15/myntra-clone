package com.example.myntra.domain.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.entity.ProductAndCategoryWithSizeAndImage
import com.example.myntra.data.remote.api.products.response.*
import com.example.myntra.domain.model.Catagory
import com.example.myntra.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(page: Int, size: Int): Flow<Resource<List<Product>>>

    fun getSingleProduct(id: String): Flow<Resource<Product>>

     fun getAllCategories(): Flow<Resource<List<Catagory>>>

    fun getSingleCategory(id: String): Flow<Resource<Category>>

}