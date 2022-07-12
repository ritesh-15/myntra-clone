package com.example.myntra.data.local.dao

import androidx.room.*
import com.example.myntra.data.local.entity.*
import com.example.myntra.domain.model.Product

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product:ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSizes(product:List<SizeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(product:List<ImageEntity>)

    @Query("SELECT * FROM products")
    suspend fun getAllProducts():List<ProductAndCategoryWithSizeAndImage>
}