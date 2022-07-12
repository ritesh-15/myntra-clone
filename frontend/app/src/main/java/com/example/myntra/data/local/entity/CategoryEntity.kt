package com.example.myntra.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.myntra.data.remote.api.products.response.Category
import com.example.myntra.domain.model.Catagory

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
)


data class CategoryWithProduct(
    @Embedded val category: CategoryEntity,
    @Relation(
        entity = ProductEntity::class,
        parentColumn = "id",
        entityColumn = "catagoryId"
    )
    val Product: List<ProductAndCategoryWithSizeAndImage>,
) {
    fun toCategory(): Category {
        return Category(
            id = category.id,
            name = category.name,
            Product = Product.map { it.toProduct() },
        )
    }

    fun toCatagory(): Catagory {
        return Catagory(
            id = category.id,
            name = category.name
        )
    }
}