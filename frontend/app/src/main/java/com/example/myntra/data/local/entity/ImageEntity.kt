package com.example.myntra.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myntra.domain.model.Image

@Entity(tableName = "images")
data class ImageEntity(
    val publicId: String,
    val url: String,
    val productId: String,
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
) {
    fun toImage(): Image {
        return Image(publicId, url)
    }
}
