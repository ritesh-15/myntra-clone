package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.ImageEntity

data class Image(
    val publicId: String,
    val url: String
){
    fun toImageEntity(productId:String):ImageEntity{
        return ImageEntity(publicId, url, productId)
    }
}