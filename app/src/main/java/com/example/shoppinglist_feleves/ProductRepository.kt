package com.example.shoppinglist_feleves

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData

class ProductRepository(application: Application) {

    private var productDao: ProductDao
    private var allProducts: LiveData<List<Product>>

    init {
        val database: ProductRoomDatabase = ProductRoomDatabase.getInstance(
            application.applicationContext
        )!!
        productDao = database.productDao()
        allProducts = productDao.getAllProduct()
    }

    fun insert(product: Product) {
        val insertAsyncTask = InsertProductAsyncTask(productDao).execute(product)
    }

    fun update(product: Product) {
        val updateAsyncTask = UpdateProductAsyncTask(productDao).execute(product)
    }

    fun delete(product: Product) {
        val deleteAsyncTask = DeleteProductAsyncTask(productDao).execute(product)
    }

    fun deleteAll() {
        val deleteAllAsyncTask = DeleteAllProductAsyncTask(productDao).execute()

    }

    fun getAllProducts(): LiveData<List<Product>> {
        return allProducts
    }

    fun getProd(type: String) : LiveData<List<Product>> {
        return productDao.getProduct(type)
    }

//    fun getProducts(type: String): LiveData<List<Product>> {
//        val deleteAsyncTask = GetProductAsyncTask(productDao).execute(type)
//    }

    companion object {
        private class InsertProductAsyncTask(productDaoParam: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            var productDao = productDaoParam
            override fun doInBackground(vararg params: Product?) {
                 productDao.insert(params[0]!!)
            }
        }
//
//        private class GetProductAsyncTask(productDaoParam: ProductDao) :
//            AsyncTask<String, Unit, Product>() {
//            var productDao = productDaoParam
//            override fun doInBackground(vararg params: String?): Product {
//                productDao.getProduct(params[0]!!)
//            }
//        }

        private class UpdateProductAsyncTask(productDao: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            val productDao = productDao
            override fun doInBackground(vararg params: Product?) {
               productDao.update(params[0]!!)
            }

        }

        private class DeleteProductAsyncTask(productDaoParam: ProductDao) :
            AsyncTask<Product, Unit, Unit>() {
            var productDao = productDaoParam
            override fun doInBackground(vararg params: Product?) {
                productDao.delete(params[0]!!)
            }
        }

        private class DeleteAllProductAsyncTask(productDaoParam: ProductDao) :
            AsyncTask<Unit, Unit, Unit>() {
            var productDao = productDaoParam
            override fun doInBackground(vararg params: Unit?) {
                productDao.deleteAll()
            }
        }
    }
}