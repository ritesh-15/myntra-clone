package com.example.myntra.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.myntra.domain.model.Product

data class ProductAndCategoryWithSizeAndImage(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
        entity = SizeEntity::class,
    )
    val sizes: List<SizeEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "productId",
        entity = ImageEntity::class,
    )
    val images: List<ImageEntity>,
) {
    fun toProduct(): Product {
        return Product(
            catagory = product.catagory,
            catagoryId = product.catagoryId,
            createdAt = product.createdAt,
            description = product.description,
            discount = product.discount,
            discountPrice = product.discountPrice,
            fabric = product.fabric,
            fit = product.fit,
            id = product.id,
            images = images.map { it -> it.toImage() },
            name = product.name,
            originalPrice = product.originalPrice,
            sizes = sizes.map { it -> it.toSize() },
            stock = product.stock,
            updatedAt = product.updatedAt,
        )
    }
}
