package com.example.myntra.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myntra.data.local.entity.CategoryEntity
import com.example.myntra.data.local.entity.CategoryWithProduct

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(categories: List<CategoryEntity>)

    @Query("SELECT * FROM categories")
    suspend fun getAllCategories():List<CategoryWithProduct>

    @Query("SELECT * FROM categories WHERE id=:categoryId")
    suspend fun getSingleCategory(categoryId:String):CategoryWithProduct
}