package com.example.shoppinglist_feleves

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAllProduct(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(product : Product)

    @Query("SELECT * FROM product_table WHERE itemType = :type")
    fun getProduct(type: String): LiveData<List<Product>>

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product_table")
    fun deleteAll()


}