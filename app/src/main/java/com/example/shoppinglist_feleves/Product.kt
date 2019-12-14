package com.example.shoppinglist_feleves

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_table")
data class Product(

    var name: String,

    var itemType: String,

    var comment: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}