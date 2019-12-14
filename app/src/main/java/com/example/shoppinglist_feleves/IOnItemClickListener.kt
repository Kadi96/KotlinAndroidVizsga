package com.example.shoppinglist_feleves

interface IOnItemClickListener {
    fun onItemClick(products: Product)
    fun onDeleteClick(products: Product)
    fun onItemTypeClick(type : String)
}