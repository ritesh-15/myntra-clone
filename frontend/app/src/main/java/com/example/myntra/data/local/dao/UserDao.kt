package com.example.myntra.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myntra.data.local.entity.AddressEntity
import com.example.myntra.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun logoutUser()

    @Query("SELECT * FROM user")
    suspend fun getUser(): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAddresses(addresses: List<AddressEntity>)

    @Query("SELECT * FROM addresses")
    suspend fun getAllAddresses(): List<AddressEntity>

    @Query("DELETE  FROM addresses")
    suspend fun removeAddressesWhenLogOut()
}