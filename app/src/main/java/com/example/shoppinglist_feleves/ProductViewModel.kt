package com.example.shoppinglist_feleves

import android.app.Application
import android.widget.MultiAutoCompleteTextView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private var productRepository: ProductRepository = ProductRepository(application)
    private var allProducts : LiveData<List<Product>> = productRepository.getAllProducts()

    fun insert(product: Product){
        productRepository.insert(product)
    }

    fun update(product: Product){
        productRepository.update(product)
    }

    fun delete(product: Product){
        productRepository.delete(product)
    }

    fun deleteAllProducts(){
        productRepository.deleteAll()
    }

    fun getAll(): LiveData<List<Product>> {
        return allProducts
    }

    fun getProduct(type: String): LiveData<List<Product>>{
        return productRepository.getProd(type)
    }





}