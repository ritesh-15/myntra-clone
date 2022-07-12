package com.example.myntra.data.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.dao.ProductDao
import com.example.myntra.data.local.entity.ProductAndCategoryWithSizeAndImage
import com.example.myntra.data.remote.api.products.ProductInterface
import com.example.myntra.data.remote.api.products.response.AllCategoriesResponse
import com.example.myntra.data.remote.api.products.response.AllProductsResponse
import com.example.myntra.data.remote.api.products.response.SingleCategoryResponse
import com.example.myntra.data.remote.api.products.response.SingleProductResponse
import com.example.myntra.domain.model.Product
import com.example.myntra.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl constructor(
    private val api: ProductInterface,
    private val dao: ProductDao,
) : ProductRepository {
    override fun getAllProducts(
        page: Int,
        size: Int,
    ): Flow<Resource<List<Product>>> =
        flow {
            emit(Resource.Loading())

            val products = dao.getAllProducts().map { it.toProduct() }
            emit(Resource.Loading(data = products))

            try {
                val response = api.getAllProducts(page, size)
                for (product in response.products) {
                    dao.insertSizes(product.sizes.map { it.toSizeEntity(product.id) })
                    dao.insertImages(product.images.map { it.toImageEntity(product.id) })
                    dao.insertProduct(product.toProductEntity())
                }
            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

            val newProducts = dao.getAllProducts().map { it.toProduct() }
            emit(Resource.Success(newProducts))
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