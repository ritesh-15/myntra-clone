package com.example.myntra.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myntra.data.local.dao.ProductDao
import com.example.myntra.data.local.entity.ImageEntity
import com.example.myntra.data.local.entity.ProductEntity
import com.example.myntra.data.local.entity.SizeEntity

@Database(
    entities = [ProductEntity::class, SizeEntity::class, ImageEntity::class],
    version = 1
)
abstract class MyDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}