package com.example.myntra.domain.model

import com.example.myntra.data.local.entity.SizeEntity

data class Size(
    val description: String,
    val id: String,
    val measurement: String,
    val title: String,
) {
    fun toSizeEntity(productId:String): SizeEntity {
        return SizeEntity(
            description, id, measurement, title, productId
        )
    }
}