package com.example.myntra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myntra.domain.model.Size

@Entity(tableName = "sizes")
data class SizeEntity(
    val description: String,
    @PrimaryKey val id: String,
    val measurement: String,
    val title: String,
    val productId: String,
) {
    fun toSize(): Size {
        return Size(
            description, id, measurement, title
        )
    }
}
