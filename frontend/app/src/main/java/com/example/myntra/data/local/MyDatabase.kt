package com.example.myntra.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myntra.data.local.dao.CartDao
import com.example.myntra.data.local.dao.CategoryDao
import com.example.myntra.data.local.dao.ProductDao
import com.example.myntra.data.local.dao.UserDao
import com.example.myntra.data.local.entity.*

@Database(
    entities = [ProductEntity::class, SizeEntity::class, ImageEntity::class,
        CategoryEntity::class, CartEntity::class, UserEntity::class, AddressEntity::class],
    version = 7,
    exportSchema = false

)
abstract class MyDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val categoryDao: CategoryDao
    abstract val cartDao: CartDao
    abstract val userDao: UserDao

}