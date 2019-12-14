package com.example.shoppinglist_feleves

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectedCategorieActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel

    companion object{
        var EXTRA_TYPE = "com.example.shoppinglist_feleves.EXTRA_TYPE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_categorie)

        title = "Termékek"

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val recyclerView: RecyclerView = findViewById(R.id.selected_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var productAdapter = ProductAdapter()
        recyclerView.adapter = productAdapter

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        if (intent.hasExtra(EXTRA_TYPE)) {
            productViewModel.getProduct(intent.getStringExtra(EXTRA_TYPE))
                .observe(this, Observer<List<Product>> {
                    productAdapter.submitList(it)
                })

        }

        productAdapter.setOnItemClickListener(object : IOnItemClickListener{
            override fun onItemClick(products: Product) {}

            override fun onItemTypeClick(type: String) {}

            override fun onDeleteClick(products: Product) {
                productViewModel.delete(products)
            }

        })
    }
}
