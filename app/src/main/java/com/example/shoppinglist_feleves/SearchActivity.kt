package com.example.shoppinglist_feleves

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchActivity : AppCompatActivity() {

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        title = "Keresés"

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel::class.java)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val recyclerView: RecyclerView = findViewById(R.id.search_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var searchAdapter = SearchItemAdapter(NewProductActivity.productTypes)
        recyclerView.adapter = searchAdapter

        searchAdapter.setOnItemClickListener(object :IOnItemClickListener{
            override fun onItemTypeClick(type: String) {
                val intent = Intent(baseContext,SelectedCategorieActivity::class.java)
                intent.putExtra(SelectedCategorieActivity.EXTRA_TYPE,type)
                startActivity(intent)
            }
            override fun onItemClick(products: Product) {
            }
            override fun onDeleteClick(products: Product) {
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

}
