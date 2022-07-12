package com.example.myntra.data.repository

import com.example.myntra.common.utils.Resource
import com.example.myntra.data.local.dao.CategoryDao
import com.example.myntra.data.local.dao.ProductDao
import com.example.myntra.data.local.entity.ProductAndCategoryWithSizeAndImage
import com.example.myntra.data.remote.api.products.ProductInterface
import com.example.myntra.data.remote.api.products.response.*
import com.example.myntra.domain.model.Catagory
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
    private val categoryDao: CategoryDao,
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

    override fun getSingleProduct(id: String): Flow<Resource<Product>> =
        flow {
            emit(Resource.Loading())
            val product = dao.getSingleProduct(id)
            emit(Resource.Loading(data = product.toProduct()))

            try {
                val response = api.getSingleProduct(id)
                dao.insertSizes(response.product.sizes.map { it.toSizeEntity(response.product.id) })
                dao.insertImages(response.product.images.map { it.toImageEntity(response.product.id) })
                dao.insertProduct(response.product.toProductEntity())

            } catch (e: HttpException) {
                val message: String =
                    "Something went wrong!"
                emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
            } catch (e: IOException) {
                emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
            }

            val newProduct = dao.getSingleProduct(id)
            emit(Resource.Success(newProduct.toProduct()))

        }

    override fun getAllCategories(): Flow<Resource<List<Catagory>>> = flow {
        emit(Resource.Loading())

        val categories = categoryDao.getAllCategories().map { it.toCatagory() }

        emit(Resource.Loading(data = categories))

        try {
            val response = api.getAllCategories()
            categoryDao.insertAllCategories(response.categories.map { it.toCategoryEntity() })
        } catch (e: HttpException) {
            val message: String =
                "Something went wrong!"
            emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
        }

        val newCategories = categoryDao.getAllCategories().map { it.toCatagory() }
        emit(Resource.Success(newCategories))
    }

    override fun getSingleCategory(id: String): Flow<Resource<Category>> = flow {
        emit(Resource.Loading())
        val category = categoryDao.getSingleCategory(id).toCategory()
        emit(Resource.Loading(data = category))

        try {
            val response = api.getSingleCategory(id)

            categoryDao.insertAllCategories(listOf(
                response.category.toCategoryEntity()
            ))

        } catch (e: HttpException) {
            val message: String =
                "Something went wrong!"
            emit(Resource.Error(message = message, errorBody = e.response()?.errorBody()))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach to the server please try again!"))
        }

        val newCategory = categoryDao.getSingleCategory(id).toCategory()
        emit(Resource.Success(data = newCategory))

    }

}